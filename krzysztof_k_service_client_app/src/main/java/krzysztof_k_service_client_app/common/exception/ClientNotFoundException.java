package krzysztof_k_service_client_app.common.exception;

/**
 * Exception is thrown when client is not found
 */
public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String message){
        super(message);
    }
}
