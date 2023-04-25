package com.shiji.autopolling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ListFilter {

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("A", "AA");
		map.put("B", "BB");
		map.put("C", "CC");
		List<String>  list = new ArrayList<String> ();
		list.add("A");
		list.add("B");
		List<String> collectData = list.stream().map(key -> (map.containsKey(key)?map.get(key):"none").toString()).collect(Collectors.toList());
		System.out.println(list+"---->"+collectData+"--->"+map);
		System.out.println(map.get(list));
		
		/*
		 * Random random = new Random(); List<User> list = new ArrayList<>();
		 * List<Integer> compare = Arrays.asList(1,2,3); for (int i = 1; i <= 20; i++) {
		 * String group = i+10 + "组";// 1-3组随机 User u = new User(i, "用户-" + i, group);
		 * list.add(u); } System.out.println("过滤前：" + list); // 按条件过滤 List<User>
		 * filterList = list.stream().filter(user
		 * ->compare.contains(user.getId())).collect(Collectors.toList());
		 * 
		 * System.out.println("过滤 后：" + filterList);
		 */
	}

	private static class User {
		Integer id;
		String name;
		String group;

		public User(Integer id, String name, String group) {
			this.id = id;
			this.name = name;
			this.group = group;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		@Override
		public String toString() {
			return "User{" + "id=" + id + ", name='" + name + '\'' + ", group='" + group + '\'' + '}';
		}
	}

}
