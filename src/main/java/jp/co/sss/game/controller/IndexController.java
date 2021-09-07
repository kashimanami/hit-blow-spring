package jp.co.sss.game.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	ArrayList<String> numberList = new ArrayList<String>();
	ArrayList<Integer> goalList = new ArrayList<Integer>();
	
	@RequestMapping(path = "/")
	public String index() {
		// ランダム3文字生成
		numberList = new ArrayList<String>();
		goalList = new ArrayList<Integer>();
		goalList = createGoalList();
		System.out.println(goalList);
		return "index";
	}

	//ランダム3文字生成
	private static ArrayList<Integer> createGoalList() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		for (int i = 1; i <= 5; i++) {
			intList.add(i);
		}
		Collections.shuffle(intList);
		ArrayList<Integer> goalList = new ArrayList<Integer>();
		goalList.add(intList.get(0));
		goalList.add(intList.get(1));
		goalList.add(intList.get(2));
		System.out.println(goalList);
		return goalList;
	}

	//Tryボタン押下
	@RequestMapping(path = "/answer", method = RequestMethod.POST)
	public String judge(Model model, Integer num1, Integer num2, Integer num3) {
		//tryNumberStr：表示用入力数字
		String tryNumberStr = num1.toString() + num2.toString() + num3.toString();
		//tryNumberList：判定用入力数字
		ArrayList<Integer> tryNumberList = new ArrayList<Integer>();
		tryNumberList.add(num1);
		tryNumberList.add(num2);
		tryNumberList.add(num3);

		//ヒットブロー判定文
		numberList.add(judgement(tryNumberList, goalList).get(1));
		if (judgement(tryNumberList, goalList).size() == 3) {
			//クリア判定文
			model.addAttribute("clearMsg", judgement(tryNumberList, goalList).get(2));
		}
		numberList.add(tryNumberStr);
		//[0]：ヒットブロー判定、[1]：表示用入力数字
		model.addAttribute("numberList", numberList);

		return "index";
	}

	//判定
	private static ArrayList<String> judgement(ArrayList<Integer> tryNumberList, ArrayList<Integer> goalList) {
		ArrayList<String> judgementList = new ArrayList<String>();
		int hit = 0;
		int blow = 0;
		if (tryNumberList.size() != 0) {
			hit = 0;
			blow = 0;
			for (int i = 0; i < goalList.size(); i++) {
				for (int j = 0; j < tryNumberList.size(); j++) {
					if (i == j && goalList.get(i) == tryNumberList.get(j)) {
						hit++;
					} else if (!(i == j) && goalList.get(i) == tryNumberList.get(j)) {
						blow++;
					}
				}
			}
			String hitblowMsg = "Hit　" + hit + "　Blow　" + blow;
			judgementList.add(String.valueOf(hit));
			judgementList.add(hitblowMsg);
		}
		if (hit == 3) {
			String clearMsg = "Clear!!";
			judgementList.add(clearMsg);
		}
		//[0]:hit数、[1]:ヒットブロー判定文、[2]:クリア判定文
		return judgementList;
	}
}
