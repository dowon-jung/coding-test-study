// 문제: 신호등
// 링크: https://school.programmers.co.kr/learn/challenges?order=acceptance_desc&levels=2&search=신호등
// 풀이일: 2026-06-11
// 접근법: 1초부터 LCM까지 브루트포스 — 모든 신호등이 동시에 노란불인 t 탐색

public class Solution {

    public int solution(int[][] signals) {
        // 각 신호등의 주기 계산
        int[] cycle = new int[signals.length];
        for (int i = 0; i < signals.length; i++) {
            cycle[i] = signals[i][0] + signals[i][1] + signals[i][2];
        }

        // 탐색 상한 = 모든 주기의 LCM
        long limit = cycle[0];
        for (int i = 1; i < cycle.length; i++) {
            limit = lcm(limit, cycle[i]);
        }

        // 1초부터 LCM까지 탐색
        for (long t = 1; t <= limit; t++) {
            if (allYellow(signals, cycle, t)) {
                return (int) t;
            }
        }

        return -1;
    }

    private boolean allYellow(int[][] signals, int[] cycle, long t) {
        for (int i = 0; i < signals.length; i++) {
            int pos = (int) ((t - 1) % cycle[i]); // 주기 내 위치 (0-indexed)
            int greenEnd = signals[i][0];           // 노란불 시작
            int yellowEnd = greenEnd + signals[i][1]; // 노란불 끝 (exclusive)
            if (pos < greenEnd || pos >= yellowEnd) return false;
        }
        return true;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println(s.solution(new int[][]{{2,1,2},{5,1,1}}));                   // 13
        System.out.println(s.solution(new int[][]{{2,3,2},{3,1,3},{2,1,1}}));            // 11
        System.out.println(s.solution(new int[][]{{3,3,3},{5,4,2},{2,1,2}}));            // 193
        System.out.println(s.solution(new int[][]{{1,1,4},{2,1,3},{3,1,2},{4,1,1}}));    // -1
    }
}
