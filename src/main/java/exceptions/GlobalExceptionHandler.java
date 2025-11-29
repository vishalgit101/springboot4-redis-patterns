package exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleLocationNotFound(LocationNotFoundException ex){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("error", "Location not found");
		body.put("message", ex.getMessage());
		body.put("status", 404);
		body.put("timestamp", System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(body);
	}

}
