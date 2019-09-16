package krzysztof_k_service_client_app.common.exception;

/**
 * Exception is thrown when parameters in methods are invalid
 */
public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String message){
        super(message);
    }
}
