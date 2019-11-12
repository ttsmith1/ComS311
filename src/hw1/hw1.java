package hw1;

public class hw1 {
	public static void main(String[] args) {
		long a = 1500450271;
		long b = 1000000007;
		long s1 = System.currentTimeMillis();
		gcd(a, b);
		long f1 = System.currentTimeMillis();
		long o1 = f1 - s1;
		long s2 = System.currentTimeMillis();
		fastgcd(a, b);
		long f2 = System.currentTimeMillis();
		long o2 = f2 - s2;
		System.out.println(o1 + "\n"  + o2);
	}
	
	static long gcd(long a, long b) {
		long n = Math.min(a, b);
		for(long i = n; i >= 1; i--) {
			if(a % i == 0 && b % i  == 0) {
				return n;
			}
		}
		return 0;
	}

	static long fastgcd(long a, long b) {
		if(b == 0) {
			return a;
		} else {
			return fastgcd(b, a % b);
		}
	}
}
