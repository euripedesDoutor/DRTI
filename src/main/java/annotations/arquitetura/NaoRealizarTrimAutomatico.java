/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package annotations.arquitetura;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//informamos qual é a retenção, ou seja, em que situação
//a marcação será usada. No nosso caso em run time, outras opções seriam: SOURCE  e CLASS.
@Retention(RetentionPolicy.RUNTIME)

//Qual elemento sofrerá anotação, no nosso caso FIELD.
//Outros valores seriam: TYPE (class e interface), METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE e PACKAGE.
@Target(ElementType.FIELD)

//declaração da Annotation, note a keywork @interface!
public @interface NaoRealizarTrimAutomatico {

}
