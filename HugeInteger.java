
import java.util.ArrayList;

class HugeInteger {
private static int rootMax = (int)Math.ceil(Math.sqrt(Integer.MAX_VALUE));

private static final int b = (int)Math.pow(10, Math.floor(Math.log10(rootMax)));
enum Sign {
    POS,
    NEG
}
private Sign sign;
private ArrayList<Integer> digits;

public static final HugeInteger ZERO = new HugeInteger("0");

public HugeInteger() {
    this.sign = Sign.POS;
    this.digits = new ArrayList<>();
}

HugeInteger(int length){
    this.sign = Sign.POS;
    this.digits = new ArrayList<Integer>();
    for (int i = 0; i < length; i++) {
        this.digits.add((int)0);
    }
}
   
HugeInteger(String str) {
    // Getting the sign
    if (str.charAt(0) == '-') {
        this.sign = Sign.NEG;
        str = str.substring(1); // Remove the negative sign from the string
    } else {
        this.sign = Sign.POS;
    }

    // Creating and populating our digits with custom base representation
    this.digits = new ArrayList<>();
    for (int i = 0; i < str.length(); i++) {
        int digit = str.charAt(i) - '0';
        base10ToBaseB(digit);
    }

    // If all digits were zeros, set sign to POS and add a single zero to digits
    if (this.digits.isEmpty()) {
        this.sign = Sign.POS;
        this.digits.add((int) 0);
    }
}

public void shiftLeft(int positions) {
    if (positions < 0) {
        throw new IllegalArgumentException("Positions must be non-negative.");
    }

    for (int i = 0; i < positions; i++) {
        this.digits.add(0, 0);
    }
}

public void shiftRight(int positions) {
    if (positions < 0) {
        throw new IllegalArgumentException("Positions must be non-negative.");
    }

    for (int i = 0; i < positions && !this.digits.isEmpty(); i++) {
        this.digits.remove(0);
    }

    // Ensure the number doesn't have leading zeros
    removeLeadingZeros();
}


private void base10ToBaseB(int digit) {
    int carry = digit;
    int position = 0;

    while (carry > 0 || position < this.digits.size()) {
        if (position < this.digits.size()) {
            carry += this.digits.get(position) * 10;
            this.digits.set(position, (int) (carry % b));
        } else {
            this.digits.add((int) (carry % b));
        }

        carry /= b;
        position++;
    }
}

public int getLength() {
    return this.digits.size();
}

public int getDigit(int index) {
    try {
        return this.digits.get(index);
    } catch (IndexOutOfBoundsException e) {
        System.out.print("invalid index");
        return -1;
    }
}

    // Convert the internal base representation to base-10
    private String baseBToBase10() {
        String stringed = "";
        for (int i = this.digits.size()-1; i >=0 ; i--) {
            stringed += String.valueOf(this.getDigit(i));
        }

        return stringed;
    }
            
    // Transform a HugeInteger to a String with base-10 representation
    public String toString() {
        String signStr = sign == Sign.NEG ? "-" : "";
        String stringed = baseBToBase10();
        
        String reversed = "";
        for (int i = stringed.length()-1; i >= 0; i--) {
            reversed+=stringed.charAt(i);
        }
        return signStr + reversed;
    }


public void print(HugeInteger i){
System.out.println(i.toString());
}
public void Assign(HugeInteger i) {
    this.sign = i.sign;
    this.digits = i.digits;
}
public void Assign(int i){
    HugeInteger temp = new HugeInteger(String.valueOf(i));
    this.sign = temp.sign;
    this.digits = temp.digits;
}


public boolean equals(HugeInteger other) {
    if (this.sign != other.sign) {
        return false;
    }

    int len1 = this.digits.size();
    int len2 = other.digits.size();

    if (len1 != len2) {
        return false;
    }

    for (int i = 0; i < len1; i++) {
        if (this.digits.get(i) != other.digits.get(i)) {
            return false;
        }
    }

    return true;
}


public boolean greaterThan(HugeInteger other) {
    if (this.sign == Sign.POS && other.sign == Sign.NEG) {
        return true;
    } else if (this.sign == Sign.NEG && other.sign == Sign.POS) {
        return false;
    }

    int len1 = this.digits.size();
    int len2 = other.digits.size();

    if (len1 > len2) {
        return this.sign == Sign.POS;
    } else if (len1 < len2) {
        return this.sign == Sign.NEG;
    }

    for (int i = len1 - 1; i >= 0; i--) {
        if (this.digits.get(i) > other.digits.get(i)) {
            return this.sign == Sign.POS;
        } else if (this.digits.get(i) < other.digits.get(i)) {
            return this.sign == Sign.NEG;
        }
    }

    return false;
}


public boolean lessThan(HugeInteger other) {
    return !this.equals(other) && !this.greaterThan(other);
}



public void add(HugeInteger other) {
    if (this.sign != other.sign) {
        HugeInteger otherCopy = new HugeInteger();
        otherCopy.Assign(other);
        otherCopy.sign = this.sign == Sign.POS ? Sign.NEG : Sign.POS;
        privateSubtract(otherCopy);
    } else {
        privateAdd(other);
    }
}

private void privateAdd(HugeInteger other) {
    int carry = 0;
    int len1 = this.digits.size();
    int len2 = other.digits.size();
    int maxLen = Math.max(len1, len2);

    for (int i = 0; i < maxLen || carry != 0; i++) {
        int sum = carry;
        if (i < len1) {
            sum += this.digits.get(i);
        }
        if (i < len2) {
            sum += other.digits.get(i);
        }

        if (i < len1) {
            this.digits.set(i, (int) (sum % b));
        } else {
            this.digits.add((int) (sum % b));
        }

        carry = sum / b;
    }
}

public void subtract(HugeInteger other) {
    if (this.sign != other.sign) {
        HugeInteger otherCopy = new HugeInteger();
        otherCopy.Assign(other);
        otherCopy.sign = this.sign == Sign.POS ? Sign.NEG : Sign.POS;
        privateAdd(otherCopy);
    } else {
        privateSubtract(other);
    }
}

private void privateSubtract(HugeInteger other) {

    if (this.equals(other)) {
        this.digits.clear();
        this.digits.add(0);
        this.sign = Sign.POS;
        return;
    }

    if (this.lessThan(other)) {
        HugeInteger temp = this;
        this.Assign(other);
        other.Assign(temp);
        this.sign = this.sign == Sign.POS ? Sign.NEG : Sign.POS;
    }

    int borrow = 0;
    for (int i = 0; i < other.digits.size() || borrow != 0; i++) {
        int thisDigit = (i < this.digits.size()) ? this.digits.get(i) : 0;
        int otherDigit = (i < other.digits.size()) ? other.digits.get(i) : 0;
        int diff = thisDigit - otherDigit - borrow;

        if (diff < 0) {
            diff += b;
            borrow = 1;
        } else {
            borrow = 0;
        }

        if (i < this.digits.size()) {
            this.digits.set(i, diff);
        } else {
            this.digits.add(diff);
        }
    }

    removeLeadingZeros();
}

private void removeLeadingZeros() {
    while (digits.size() > 1 && digits.get(digits.size() - 1) == 0) {
        digits.remove(digits.size() - 1);
    }

    // If the number has only one digit and it's zero, set the sign to positive
    if (digits.size() == 1 && digits.get(0) == 0) {
        sign = Sign.POS;
    }
}

public void mult(HugeInteger other) {
    HugeInteger result = karatsubaMultiply(this, other);
    this.Assign(result);
}

private  HugeInteger karatsubaMultiply(HugeInteger x, HugeInteger y) {
    int n = Math.max(x.digits.size(), y.digits.size());

    if (x.digits.size() <= 10|| y.digits.size()<=10) {
        return simpleMultiply(x, y);
    }
    n = (n / 2) + (n % 2);

    HugeInteger[] xSplit = x.splitAt(n);
    HugeInteger[] ySplit = y.splitAt(n);

    HugeInteger a = xSplit[0];
    HugeInteger b = xSplit[1];
    HugeInteger c = ySplit[0];
    HugeInteger d = ySplit[1];

    HugeInteger ac = karatsubaMultiply(a, c);
    HugeInteger bd = karatsubaMultiply(b, d);
    HugeInteger abcd = karatsubaMultiply(a.addAndReturnNew(b), c.addAndReturnNew(d));

    abcd.subtract(ac);
    abcd.subtract(bd);

    ac.shiftLeft(2 * n);
    abcd.shiftLeft(n);

    ac.add(abcd);
    ac.add(bd);

    ac.sign = x.sign == y.sign ? Sign.POS : Sign.NEG;

    return ac;
}

private  HugeInteger simpleMultiply(HugeInteger x, HugeInteger y) {
    HugeInteger result = new HugeInteger();
    int carry;

    for (int i = 0; i < x.digits.size(); i++) {
        carry = 0;
        for (int j = 0; j < y.digits.size(); j++) {
            int product = x.digits.get(i) * y.digits.get(j) + carry;

            if (i + j < result.digits.size()) {
                product += result.digits.get(i + j);
                result.digits.set(i + j, product % b);
            } else {
                result.digits.add(product % b);
            }

            carry = product / b;
        }

        if (carry > 0) {
            result.digits.add(carry);
        }
    }

    result.sign = x.sign == y.sign ? Sign.POS : Sign.NEG;
    result.removeLeadingZeroes();
    return result;
}

private HugeInteger[] splitAt(int position) {
    HugeInteger lower = new HugeInteger();
    HugeInteger upper = new HugeInteger();

    for (int i = 0; i < this.digits.size(); i++) {
        if (i < position) {
            lower.digits.add(this.digits.get(i));
        } else {
            upper.digits.add(this.digits.get(i));
        }
    }

    return new HugeInteger[]{upper, lower};
}


private HugeInteger addAndReturnNew(HugeInteger other) {
    HugeInteger result = new HugeInteger();
    result.Assign(this);
    result.add(other);
    return result;
}


public void divide(HugeInteger other) {
    if (other.isZero()) {
        throw new ArithmeticException("Division by zero");
    }

    HugeInteger divisor = new HugeInteger();
    divisor.Assign(other);
    divisor.sign = Sign.POS;

    HugeInteger dividend = new HugeInteger();
    dividend.Assign(this);
    dividend.sign = Sign.POS;

    HugeInteger quotient = new HugeInteger();
    HugeInteger temp = new HugeInteger("1");

    while (dividend.greaterThan(divisor) || dividend.equals(divisor)) {
        dividend.subtract(divisor);
        quotient.add(temp);
    }

    quotient.sign = (this.sign == other.sign) ? Sign.POS : Sign.NEG;
    this.Assign(quotient);
}




public void longDivide(HugeInteger other) {
    if (other.equals(ZERO)) {
        throw new ArithmeticException("Cannot divide by zero.");
    }

    HugeInteger quotient = new HugeInteger();
    HugeInteger remainder = new HugeInteger();
    remainder.Assign(this);

    if (this.lessThan(other)) {
        this.Assign(quotient); // quotient is 0, remainder is this
    }

    int lengthDiff = this.digits.size() - other.digits.size();
    other.shiftLeft(lengthDiff);
    HugeInteger one = new HugeInteger("1");

    while (lengthDiff >= 0) {
        while (!remainder.lessThan(other)) {
            remainder.subtract(other);
            HugeInteger q = new HugeInteger(String.valueOf((int) Math.pow(b, lengthDiff)));
            quotient.add(q);
        }
        other.shiftRight(1);
        lengthDiff--;
    }

    quotient.sign = this.sign == other.sign ? Sign.POS : Sign.NEG;
    this.Assign(quotient);
}



public boolean isZero() {
    for (int digit : digits) {
        if (digit != 0) {
            return false;
        }
    }
    return true;
}



private void removeLeadingZeroes() {
    int lastNonZeroIndex = -1;
    for (int i = digits.size() - 1; i >= 0; i--) {
        if (digits.get(i) != 0) {
            lastNonZeroIndex = i;
            break;
        }
    }

    if (lastNonZeroIndex == -1) { 
        digits.clear();
        digits.add(0);
        sign = Sign.POS; 
    } else if (lastNonZeroIndex < digits.size() - 1) {
        digits.subList(lastNonZeroIndex + 1, digits.size()).clear();
    }
}





//46341
//10000
public static void main(String[] args) {
HugeInteger h = new HugeInteger("37975243679");
HugeInteger i = new HugeInteger("4009972");

h.karatsubaMultiply(i,h);
i.print(h);
}

}