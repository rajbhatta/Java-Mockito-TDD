## How to write unit test for method containing exception in Java ? ##
### 1. Non-Void Return Type ###
First, if our method return type is not void we can use <b>when().thenThrow(): </b>
```java

@Test(expected = NullPointerException.class)
public void whenConfigNonVoidRetunMethodToThrowEx_thenExIsThrown() {
    MyDictionary dictMock = mock(MyDictionary.class);
    when(dictMock.getMeaning(anyString()))
      .thenThrow(NullPointerException.class);
 
    dictMock.getMeaning("word");
}
```
Notice, we configured the getMeaning() method – which returns a value of type String – to throw a NullPointerException when called.

### 2. Void return type ###
Now, if our method returns void, we'll use <b> doThrow(): </b>
```java
@Test(expected = IllegalStateException.class)
public void whenConfigVoidRetunMethodToThrowEx_thenExIsThrown() {
    MyDictionary dictMock = mock(MyDictionary.class);
    doThrow(IllegalStateException.class)
      .when(dictMock)
      .add(anyString(), anyString());
 
    dictMock.add("word", "meaning");
}
```
Here, we configured an add() method – which returns void – to throw IllegalStateException when called.

We can't use when().thenThrow() with void return type as the compiler doesn't allow void methods inside brackets.