package at.technikum_wien.tourplannerapi.exception;

public class ResourceNotFoundExeption extends RuntimeException {
    public ResourceNotFoundExeption(String message) {
        super(message);
    }
}
