package com.newbig.im.app.advice;

import com.newbig.im.common.exception.TokenException;
import com.newbig.im.common.exception.UserRemindException;
import com.newbig.im.common.serializer.AppDateEditor;
import com.newbig.im.model.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.newbig.im.model.vo.ResponseVo.failure;

@ControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(value = UserRemindException.class)
    @ResponseBody
    public ResponseVo<String> exception(UserRemindException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex.getMessage());
        setResponse(response);
        return failure(ex.getMessage());
    }
    @ExceptionHandler(value = TokenException.class)
    @ResponseBody
    public ResponseVo<String> exception(TokenException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex.getMessage());
        setResponse(response,HttpStatus.UNAUTHORIZED);
        return failure(ex.getMessage(),"/#/login");
    }
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseVo<String> exception(BindException ex, HttpServletRequest
            request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex);
        setResponse(response);
        BindingResult bindingResult = ex.getBindingResult();
        return failure(bindingResult.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseVo<String> exception(MissingServletRequestParameterException ex, HttpServletRequest
            request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex);
        setResponse(response);
        return failure("参数缺失");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo<String> exception(MethodArgumentNotValidException ex, HttpServletRequest
            request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex);
        setResponse(response);
        BindingResult bindingResult = ex.getBindingResult();
        return failure(bindingResult.getFieldErrors().get(0).getField() + bindingResult.getFieldErrors().get(0).getDefaultMessage());
        //只返回第一条
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseVo<String> exception(DataIntegrityViolationException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex);
        setResponse(response);
        if (ex.getCause().getMessage().contains("Data too long for column")) {
            Pattern pattern = Pattern.compile(".*Data too long for column '(.*)' at row.*");
            Matcher matcher = pattern.matcher(ex.getCause().getMessage());
            if (matcher.matches()) {
                String key = matcher.group(1);
                return failure("字段[" + key + "]长度超出数据库限制");
            }
            return failure("某些字段长度超出数据库限制");
        } else if (ex.getCause().getMessage().contains("doesn't have a default value")) {
            Pattern pattern = Pattern.compile(".*Field '(.*)' doesn't have a default value.*");
            Matcher matcher = pattern.matcher(ex.getCause().getMessage());
            if (matcher.matches()) {
                String key = matcher.group(1);
                return failure("字段[" + key + "]没有默认值");
            }
            return failure("某些字段没有默认值");
        } else {
            return failure(ex.getCause().getMessage());
        }
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public ResponseVo<String> duplicateKeyException(DuplicateKeyException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex);
        setResponse(response);
        Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");
        Matcher matcher = pattern.matcher(ex.getCause().getMessage());
        if (matcher.matches()) {
            String key = matcher.group(2);
            String value = matcher.group(1);
            return failure(key.replace("_UNIQUE", "") + ":" + value + " 已存在，请修改后重新添加");
        } else {
            return failure("系统有重复数据");
        }
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVo<String> exception(Exception ex, HttpServletRequest
            request, HttpServletResponse response) {
        log.error("uri:{},exception message:{}", request.getRequestURI(), ex);
        setResponse(response);
        return failure("系统异常");
    }

    private void setResponse(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }
    private void setResponse(HttpServletResponse response,HttpStatus httpStatus){
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setStatus(httpStatus.value());
    }
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        webDataBinder.registerCustomEditor(Date.class, new AppDateEditor(true));
    }
}
