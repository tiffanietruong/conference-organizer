package requests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestManager implements Serializable {
    private final List<Request> requests;
    private final RequestFactory factory;

    /**
     * Constructs a manager responsible for request data that is initialized storing an empty list of requests.
     */
    public RequestManager() {
        this.requests = new ArrayList<>();
        this.factory = new RequestFactory();
    }

    /**
     * Gets the Ids of all requests
     *
     * @return list of request Ids
     */
    public List<UUID> getRequestsIds() {
        List<UUID> requestIds = new ArrayList<>();
        for (Request request : requests) {
            requestIds.add(request.getId());
        }
        return requestIds;
    }

    /**
     * Gets request with given id
     *
     * @param requestId the id of the desired request
     * @return the desired request
     */
    public Request getRequestWithId(UUID requestId) {
        for (Request request : requests) {
            if (request.getId().equals(requestId)) {
                return request;
            }
        }
        throw new RequestNotFoundException(String.format("There are no requests with the ID %s.",
                requestId.toString()));
    }

    /**
     * Adds a request and the author of the request
     *
     * @param author the author
     * @param text   the text of the request
     */
    public void addRequest(String text, String author) {
        Request request = factory.getRequest(text, author);
        requests.add(request);
    }

    /**
     * Deletes a request
     *
     * @param requestId id of request to be deleted
     */
    public void deleteRequest(UUID requestId) {
        requests.removeIf(request -> request.getId().equals(requestId));
    }

    /**
     * Checks if user is author of request
     *
     * @param requestId id of request
     * @param username  username of user
     * @return true if user is requester, false otherwise
     */
    public boolean isAuthor(String username, UUID requestId) {
        Request message = getRequestWithId(requestId);
        return message.getAuthor().equals(username);
    }

    /**
     * Adds reply to a request
     *
     * @param requestId id of request
     * @param text      text of the reply
     * @param author    author of the reply
     */
    public void addReply(String text, UUID requestId, String author) {
        Request request = getRequestWithId(requestId);
        request.setReply(text);
        request.setReplyAuthor(author);
    }

    /**
     * Checks if request has reply
     *
     * @param requestId the id of the request
     * @return true if reply exists, false otherwise
     */
    public boolean hasReply(UUID requestId) {
        return !getRequestWithId(requestId).getReply().equals("");
    }

    /**
     * Updates status fo a request
     *
     * @param requestId id of request
     */
    public void updateStatus(UUID requestId) {
        Request request = getRequestWithId(requestId);
        request.setStatus(true);
    }

    /**
     * Gets the list of the user's events
     *
     * @param username username of the user
     * @return list of ids of user's requests
     */
    public List<UUID> userRequests(String username) {
        List<UUID> requests = new ArrayList<>();
        for (UUID requestId : getRequestsIds()) {
            if (isAuthor(username, requestId)) {
                requests.add(requestId);
            }
        }
        return requests;
    }

}