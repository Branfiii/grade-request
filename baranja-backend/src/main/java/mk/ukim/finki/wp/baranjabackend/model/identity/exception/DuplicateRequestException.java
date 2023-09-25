package mk.ukim.finki.wp.baranjabackend.model.identity.exception;

import mk.ukim.finki.wp.baranjabackend.model.Request;
import mk.ukim.finki.wp.baranjabackend.model.RequestType;

public class DuplicateRequestException  extends RuntimeException{
    public DuplicateRequestException(String requestType, String professorName, String subName) {
        super(String.format("Imate veke podneseno baranje od tip %s kaj profesor %s i predmet %s", requestType, professorName, subName));
    }
}
