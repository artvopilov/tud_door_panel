package tu.foxtrot.foxtrotdoorpanelmobileapp.network.responseObjects;

/**
 * The type Login response.
 */
public class LoginResponse {
    private Worker worker;
    private String token;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    /**
     * Gets worker.
     *
     * @return the worker
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Sets worker.
     *
     * @param worker the worker
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
