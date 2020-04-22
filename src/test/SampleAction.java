public class SampleAction extends Action {
    String message;
    public SampleAction(String message) {
        super(Type.SAMPLE);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
