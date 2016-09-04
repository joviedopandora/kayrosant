/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author breyner
 */
public class ValidarEmail implements Validator {

    public ValidarEmail() {
        // <f:validator validatorId="PandoraAudIntegral.validarEmail"/>
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String enteredEmail = (String) value;
        if (enteredEmail != null && !enteredEmail.equals("")) {
            //Set the email pattern string
            Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

            //Match the given string with the pattern
            Matcher m = p.matcher(enteredEmail);

            //Check whether match is found
            boolean matchFound = m.matches();

            if (!matchFound) {
                FacesMessage message = new FacesMessage();
                message.setDetail("Error Formato Email");
                message.setSummary("Error Formato Email");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }
}
