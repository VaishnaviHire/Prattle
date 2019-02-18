package edu.northeastern.ccs.im.server;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

import edu.northeastern.ccs.im.ChatLogger;
import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.NetworkConnection;

/**
 * Instances of this class handle all of the incoming communication from a
 * single IM client. Instances are created when the client signs-on with the
 * server. After instantiation, it is executed periodically on one of the
 * threads from the thread pool and will stop being run only when the client
 * signs off.
 * 
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/4.0/. It is based on work
 * originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 * 
 * @version 1.3
 */
public class ClientRunnable implements Runnable {
	/**
	 * Utility class which we will use to send and receive communication to this
	 * client.
	 */
	private NetworkConnection connection;

	/** Id for the user for whom we use this ClientRunnable to communicate. */
	private int userId;

	/** Name that the client used when connecting to the server. */
	private String name;

	/**
	 * Whether this client has been initialized, set its user name, and is ready to
	 * receive messages.
	 */
	private boolean initialized;

	/**
	 * Whether this client has been terminated, either because he quit or due to
	 * prolonged inactivity.
	 */
	private boolean terminate;

	/** The timer that keeps track of the clients activity. */
	private ClientTimer timer;

	/**
	 * The future that is used to schedule the client for execution in the thread
	 * pool.
	 */
	private ScheduledFuture<?> runnableMe;

	/** Collection of messages queued up to be sent to this client. */
	private Queue<Message> waitingList;

	/**
	 * Create a new thread with which we will communicate with this single client.
	 * 
	 * @param network NetworkConnection used by this new client
	 */
	public ClientRunnable(NetworkConnection network) {
		// Create the class we will use to send and receive communication
		connection = network;
		// Mark that we are not initialized
		initialized = false;
		// Mark that we are not terminated
		terminate = false;
		// Create the queue of messages to be sent
		waitingList = new ConcurrentLinkedQueue<>();
		// Mark that the client is active now and start the timer until we
		// terminate for inactivity.
		timer = new ClientTimer();
	}

	/**
	 * Check to see for an initialization attempt and process the message sent.
	 */
	private void checkForInitialization() {
		// Check if there are any input messages to read
		Iterator<Message> messageIter = connection.iterator();
		if (messageIter.hasNext()) {
			// If a message exists, try to use it to initialize the connection
			Message msg = messageIter.next();
			if (setUserName(msg.getName())) {
				// Update the time until we terminate this client due to inactivity.
				timer.updateAfterInitialization();
				// Set that the client is initialized.
				initialized = true;
			} else {
				initialized = false;
			}
		}
	}

	/**
	 * Check if the message is properly formed. At the moment, this means checking
	 * that the identifier is set properly.
	 * 
	 * @param msg Message to be checked
	 * @return True if message is correct; false otherwise
	 */
	private boolean messageChecks(Message msg) {
		// Check that the message name matches.
		return (msg.getName() != null) && (msg.getName().compareToIgnoreCase(getName()) == 0);
	}

	/**
	 * Immediately send this message to the client. This returns if we were
	 * successful or not in our attempt to send the message.
	 * 
	 * @param message Message to be sent immediately.
	 * @return True if we sent the message successfully; false otherwise.
	 */
	private boolean sendMessage(Message message) {
		ChatLogger.info("\t" + message);
		return connection.sendMessage(message);
	}

	/**
	 * Try allowing this user to set his/her user name to the given username.
	 * 
	 * @param userName The new value to which we will try to set userName.
	 * @return True if the username is deemed acceptable; false otherwise
	 */
	private boolean setUserName(String userName) {
		boolean result = false;
		// Now make sure this name is legal.
		if (userName != null) {
			// Optimistically set this users ID number.
			setName(userName);
			userId = hashCode();
			result = true;
		} else {
			// Clear this name; we cannot use it. *sigh*
			userId = -1;
		}
		return result;
	}

	/**
	 * Add the given message to this client to the queue of message to be sent to
	 * the client.
	 * 
	 * @param message Complete message to be sent.
	 */
	public void enqueueMessage(Message message) {
		waitingList.add(message);
	}

	/**
	 * Get the name of the user for which this ClientRunnable was created.
	 * 
	 * @return Returns the name of this client.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the user for which this ClientRunnable was created.
	 * 
	 * @param name The name for which this ClientRunnable.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the user for which this ClientRunnable was created.
	 * 
	 * @return Returns the current value of userName.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Return if this thread has completed the initialization process with its
	 * client and is read to receive messages.
	 * 
	 * @return True if this thread's client should be considered; false otherwise.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Perform the periodic actions needed to work with this client.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		// The client must be initialized before we can do anything else
		if (!initialized) {
			checkForInitialization();
		} else {
			handleIncomingMessages();
			handleOutgoingMessages();
		}
		// Finally, check if this client have been inactive for too long and,
		// when they have, terminate the client.
		if (timer.isBehind()) {
			ChatLogger.error("Timing out or forcing off a user " + name);
			terminate = true;
		}
		if (terminate) {
			terminateClient();
		}
	}

	/**
	 * Checks incoming messages and performs appropriate actions based on the type
	 * of message.
	 */
	protected void handleIncomingMessages() {
		// Client has already been initialized, so we should first check
		// if there are any input
		// messages.
		Iterator<Message> messageIter = connection.iterator();
		if (messageIter.hasNext()) {
			// Get the next message
			Message msg = messageIter.next();
			// If the message is a broadcast message, send it out
			if (msg.terminate()) {
				// Stop sending the poor client message.
				terminate = true;
				// Reply with a quit message.
				enqueueMessage(Message.makeQuitMessage(name));
			} else {
				// Check if the message is legal formatted
				if (messageChecks(msg)) {
					// Check for our "special messages"
					if (msg.isBroadcastMessage()) {
						// Check for our "special messages"
						Prattle.broadcastMessage(msg);
					}
				} else {
					Message sendMsg;
					sendMsg = Message.makeBroadcastMessage(ServerConstants.BOUNCER_ID,
							"Last message was rejected because it specified an incorrect user name.");
					enqueueMessage(sendMsg);
				}
			}
		}
	}

	/**
	 * Sends the enqueued messages to the printer and makes sure they were sent out.
	 */
	protected void handleOutgoingMessages() {
		// Check to make sure we have a client to send to.
		boolean keepAlive = true;
		if (!waitingList.isEmpty()) {
			keepAlive = false;
			// Send out all of the message that have been added to the
			// queue.
			do {
				Message msg = waitingList.remove();
				boolean sentGood = sendMessage(msg);
				keepAlive |= sentGood;
				// Update the time until we terminate the client for inactivity.
				timer.updateAfterActivity();

			} while (!waitingList.isEmpty());
		}
		terminate |= !keepAlive;
	}

	/**
	 * Store the object used by this client runnable to control when it is scheduled
	 * for execution in the thread pool.
	 * 
	 * @param future Instance controlling when the runnable is executed from within
	 *               the thread pool.
	 */
	public void setFuture(ScheduledFuture<?> future) {
		runnableMe = future;
	}

	/**
	 * Terminate a client that we wish to remove. This termination could happen at
	 * the client's request or due to system need.
	 */
	public void terminateClient() {
		// Once the communication is done, close this connection.
		connection.close();
		// Remove the client from our client listing.
		Prattle.removeClient(this);
		// And remove the client from our client pool.
		runnableMe.cancel(false);
	}
}