package com.mock.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2016/12/17.
 */
public class Not999Validator implements ConstraintValidator<Not999,Long>{
    @Override
    public void initialize(Not999 arg0) {

    }

    @Override
    public boolean isValid(Long vaule, ConstraintValidatorContext context) {
        if(vaule==999){
            return false;
        }else{
            return true;
        }
    }
}
