package net.nemo.whatever.db.mapper;

import net.nemo.whatever.entity.User;

import java.util.List;

public interface UserMapper {

	int insert(User user);
	int update(User user);
	
	User findByEmail(String email);
	User findById(Integer id);
	User findByOpenId(String openId);

    List<String> findContacts(Integer userId);
}
