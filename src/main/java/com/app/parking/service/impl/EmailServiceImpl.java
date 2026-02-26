package com.app.parking.service.impl;

import com.app.parking.dto.response.BookingResponse;
import com.app.parking.entity.ParkingData;
import com.app.parking.entity.User;
import com.app.parking.entity.Wallet;
import com.app.parking.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    @Async
    @Override
    public void sendRechargeEmail(Wallet wallet, Long amount, LocalDate localDate, LocalTime localTime){
        Context context = getRechargeContext(wallet, amount, localDate, localTime);

        String htmlContent = templateEngine.process("recharge-confirmation", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(wallet.getUser().getEmail());
            mimeMessageHelper.setSubject("Recharge Confirmation");
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            LOGGER.info("Recharge confirmation email sent for user: {} to email: {}", wallet.getUser().getUserId(), wallet.getUser().getEmail());
        }catch (MessagingException e){
            throw new RuntimeException("Failed to send email to: "+wallet.getUser().getEmail());
        }
    }




    private static Context getRechargeContext(Wallet wallet, Long amount, LocalDate localDate, LocalTime localTime) {
        Context context = new Context();
        context.setVariable("userName", wallet.getUser().getUsername());
        context.setVariable("userEmail", wallet.getUser().getEmail());
        context.setVariable("walletId", wallet.getWalletId());
        context.setVariable("amount", amount);
        context.setVariable("balance", wallet.getBalance());
        context.setVariable("date", localDate);
        context.setVariable("time", localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a")));

        return context;
    }


    @Async
    @Override
    public void sendListingEmail(ParkingData parkingData){
        Context context = getListingContext(parkingData);

        String htmlContent = templateEngine.process("listing-confirmation.html", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(parkingData.getOwner().getEmail());
            mimeMessageHelper.setSubject("Listing Confirmation");
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            LOGGER.info("Listing confirmation email sent for owner: {} to email: {}", parkingData.getOwner().getUserId(), parkingData.getOwner().getEmail());
        }catch (MessagingException e){
            throw new RuntimeException("Failed to send email to: "+parkingData.getOwner().getEmail());
        }
    }

    private static Context getListingContext(ParkingData parkingData){
        Context context = new Context();

        context.setVariable("parkingId", parkingData.getParkingId());
        context.setVariable("disabled", parkingData.getDisabled());
        context.setVariable("price", parkingData.getPrice());
        context.setVariable("state", parkingData.getState());
        context.setVariable("city", parkingData.getCity());
        context.setVariable("pinCode", parkingData.getPincode());
        context.setVariable("addressLine", parkingData.getAddressLine());
        context.setVariable("vehicleType", parkingData.getVehicleType());
        context.setVariable("listingDate", parkingData.getCreatedAt());

        return context;
    }

    @Async
    @Override
    public void sendBookingEmail(ParkingData parkingData, User user, BookingResponse bookingResponse){
        Context context = getBookingContext(parkingData, user, bookingResponse);

        String htmlContent = templateEngine.process("booking-confirmation.html", context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("Booking Confirmation");
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            LOGGER.info("Booking confirmation email sent for user: {} to email: {}", user.getUserId(), user.getEmail());
        }catch (MessagingException e){
            throw new RuntimeException("Failed to send email to: "+user.getEmail());
        }
    }

    private static Context getBookingContext(ParkingData parkingData, User user, BookingResponse bookingResponse){
        Context context = new Context();

        context.setVariable("parkingId", parkingData.getParkingId());
        context.setVariable("price", parkingData.getPrice());
        context.setVariable("state", parkingData.getState());
        context.setVariable("city", parkingData.getCity());
        context.setVariable("pinCode", parkingData.getPincode());
        context.setVariable("addressLine", parkingData.getAddressLine());
        context.setVariable("locationUrl", parkingData.getLocationUrl());
        context.setVariable("vehicleType", parkingData.getVehicleType());



        context.setVariable("name", user.getFullName());
        context.setVariable("duration", bookingResponse.getDuration());
        context.setVariable("totalAmount", bookingResponse.getTotalBill());
        context.setVariable("bookingDate", bookingResponse.getBookedAt());
        context.setVariable("endDate", bookingResponse.getBookingEndDate());
        context.setVariable("endTime", bookingResponse.getBookingEndTime().format(DateTimeFormatter.ofPattern("HH:mm:ss a")));

        return context;
    }
}
