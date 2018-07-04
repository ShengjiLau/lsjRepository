package com.lcdt.traffic.web.controller.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/11/23.
 */
@RequestMapping("/debug")
@RestController
public class DebugController {



	@GetMapping("/a")
	@PreAuthorize("hasAnyAuthority('update_company_info')")
	public String nullException(){
		String a = null;
		System.out.println(1/0);
		return a;
	}


}
