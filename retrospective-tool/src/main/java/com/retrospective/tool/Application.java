package com.retrospective.tool;

import com.retrospective.tool.repositories.MemberRepository;

import com.retrospective.tool.models.*;

import com.retrospective.tool.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;


@RequiredArgsConstructor
@SpringBootApplication
public class Application {

	@Autowired
	private MemberRepository memberRepository;



	@Autowired
	private EmailSenderService service;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


	}
	@Scheduled(cron = "0 9 30 * * ?")
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail(){
		for(Member member : memberRepository.findAll()){
			service.sendSimpleEmail(member.getEmail(),"Daily reminder for your retrospective meeting","Retrospective Meeting Reminder");
		}
	}



}
