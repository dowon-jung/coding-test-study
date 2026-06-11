// 문제: 완주하지 못한 선수
// 링크: https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=완주하지+못한+선수
// 풀이일: 2026-06-11
// 접근법: HashMap으로 참가자 빈도 카운팅 후 완주자와 비교

import java.util.HashMap;

public class Solution {

    public String solution(String[] participant, String[] completion) {
        HashMap<String, Integer> map = new HashMap<>();

        for (String p : participant) {
            map.put(p, map.getOrDefault(p, 0) + 1);
        }

        for (String c : completion) {
            map.put(c, map.get(c) - 1);
        }

        for (String key : map.keySet()) {
            if (map.get(key) > 0) return key;
        }

        return "";
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        // 테스트 케이스 1
        System.out.println(s.solution(
            new String[]{"leo", "kiki", "eden"},
            new String[]{"eden", "kiki"}
        )); // 기대값: leo

        // 테스트 케이스 2 (동명이인)
        System.out.println(s.solution(
            new String[]{"marina", "josipa", "nikola", "vinko", "filipa"},
            new String[]{"josipa", "filipa", "marina", "nikola"}
        )); // 기대값: vinko
    }
}
