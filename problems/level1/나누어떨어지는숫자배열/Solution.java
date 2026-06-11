// 문제: 나누어 떨어지는 숫자 배열
// 링크: https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=1&search=나누어+떨어지는+숫자+배열
// 풀이일: 2026-06-11
// 접근법: ArrayList에 조건 맞는 것만 담고 정렬 후 int[]로 변환

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Solution {

    public int[] solution(int[] arr, int divisor) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int n : arr) {
            if (n % divisor == 0) {
                list.add(n);
            }
        }

        if (list.isEmpty()) return new int[]{-1};

        Collections.sort(list);

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println(Arrays.toString(s.solution(new int[]{5, 9, 7, 10}, 5)));   // [5, 10]
        System.out.println(Arrays.toString(s.solution(new int[]{2, 36, 1, 3}, 1)));   // [1, 2, 3, 36]
        System.out.println(Arrays.toString(s.solution(new int[]{3, 2, 6}, 10)));       // [-1]
    }
}
