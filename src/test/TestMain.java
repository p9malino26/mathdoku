public class TestMain {
    public static void main(String[] args) {
        LimitedStack stack = new LimitedStack(3);
        stack.push(new SampleAction("aardvark"));
        stack.push(new SampleAction("pig"));
        stack.push(new SampleAction("dog"));
        stack.push(new SampleAction("sheep"));
        stack.push(new SampleAction("wolf"));
        stack.push(new SampleAction("horse"));
        Action temp = stack.pop();
        temp = stack.pop();
        temp = stack.pop();
    }
}
