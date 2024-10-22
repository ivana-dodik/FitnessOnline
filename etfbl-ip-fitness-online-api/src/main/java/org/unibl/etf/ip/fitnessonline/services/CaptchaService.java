package org.unibl.etf.ip.fitnessonline.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

    @Value("${captcha.secret}")
    private String captchaSecret;

    public boolean verifyCaptcha(String captchaResponse, int answer) {
        try {
            int userAnswer = Integer.parseInt(captchaResponse);
            return answer == userAnswer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
