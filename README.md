# HugeInteger

This is a Java library that allows you to handle arbitrarily large integers. The library provides a `HugeInteger` class with various methods for performing operations on large integers.

## Features

- Create a `HugeInteger` object with a specified length or from a string representation
- Get the length of a `HugeInteger` object
- Retrieve a digit at a specific index of a `HugeInteger` object
- Compare two `HugeInteger` objects for equality, greater than, or less than
- Assign the value of one `HugeInteger` object to another
- Add, multiply, and divide two `HugeInteger` objects
- Convert a `HugeInteger` object to a string representation

## Methods

### Constructor

- `public HugeInteger(int length)`: Creates a `HugeInteger` object with the specified length.
- `public HugeInteger(String str)`: Creates a `HugeInteger` object from the given string representation.

### Getter

- `public int getLength()`: Returns the length of the `HugeInteger` object.
- `public byte getDigit(int index)`: Returns the digit at the specified index of the `HugeInteger` object.

### Comparison

- `public boolean equal(HugeInteger i)`: Compares the current `HugeInteger` object with the given `HugeInteger` object for equality. Returns `true` if they are equal, `false` otherwise.
- `public boolean greaterThan(HugeInteger i)`: Compares the current `HugeInteger` object with the given `HugeInteger` object. Returns `true` if the current object is greater than the given object, `false` otherwise.
- `public boolean lessThan(HugeInteger i)`: Compares the current `HugeInteger` object with the given `HugeInteger` object. Returns `true` if the current object is less than the given object, `false` otherwise.

### Assignment

- `public void assign(HugeInteger i)`: Assigns the value of the given `HugeInteger` object to the current object.

### Operations

- `public void add(HugeInteger i)`: Adds the given `HugeInteger` object to the current object, modifying the current object.
- `public void mult(HugeInteger i)`: Multiplies the current `HugeInteger` object with the given `HugeInteger` object, modifying the current object.
- `public void divide(HugeInteger i)`: Divides the current `HugeInteger` object by the given `HugeInteger` object, modifying the current object.

### Conversion

- `public String toString()`: Returns a string representation of the `HugeInteger` object.

## Usage

```java
// Create a HugeInteger object with length 10
HugeInteger hugeInt1 = new HugeInteger(10);

// Create a HugeInteger object from a string
HugeInteger hugeInt2 = new HugeInteger("12345678901234567890");

// Get the length of a HugeInteger object
int length = hugeInt2.getLength(); // length = 20

// Retrieve a digit at a specific index
byte digit = hugeInt2.getDigit(5); // digit = 6

// Compare two HugeInteger objects
boolean isEqual = hugeInt1.equal(hugeInt2); // isEqual = false
boolean isGreater = hugeInt2.greaterThan(hugeInt1); // isGreater = true
boolean isLess = hugeInt1.lessThan(hugeInt2); // isLess = true

// Assign a HugeInteger object to another
hugeInt1.assign(hugeInt2);

// Add two HugeInteger objects
HugeInteger hugeInt3 = new HugeInteger("98765432109876543210");
hugeInt2.add(hugeInt3);

// Multiply two HugeInteger objects
hugeInt1.mult(hugeInt3);

// Divide a HugeInteger object by another
hugeInt2.divide(hugeInt3);

// Convert a HugeInteger object to a string
String strRepresentation = hugeInt1.toString();
