package ya.moguchi.internal;

class Solution
{

    static Solution of(int state)
    {
        return new Solution(state, "");
    }

    static Solution of(int state, String raw)
    {
        return new Solution(state, raw);
    }

    private int state;
    private String raw;

    private Solution(int state, String raw)
    {
        this.state = state;
        this.raw = raw;
    }

    int getState()
    {
        return state;
    }

    String getRaw()
    {
        return raw;
    }
}
