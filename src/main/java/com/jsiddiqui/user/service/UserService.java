package com.jsiddiqui.user.service;

import com.jsiddiqui.user.VO.Department;
import com.jsiddiqui.user.VO.ResponseTemplateVO;
import com.jsiddiqui.user.entity.User;
import com.jsiddiqui.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        Department department =
                restTemplate.getForObject("http://localhost:9001/departments/"+
                        user.getDepartmentId(), Department.class);
        responseTemplateVO.setDepartment(department);
        responseTemplateVO.setUser(user);
        return responseTemplateVO;
    }
}
