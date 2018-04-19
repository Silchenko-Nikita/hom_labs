public abstract class FSM {
    enum State {
        q0, q1, q2, q3, ERROR
    }

    enum Event {
        UPPER_LETTER("ABCDEFGHIJKLMNOPQRSTUVWXYZ"), COMMA(","),
        WHITESPACE(" \t\n\r\f"), LEFT_QUOTE("("), RIGHT_QUOTE(")"),
        ANY;

        private String chars;

        Event() {}

        Event(String chars){
            this.chars = chars;
        }

        public boolean contains(char ch) {
            return chars != null && chars.contains(String.valueOf(ch));
        }

        static Event getEvent(char ch){
            for (Event event : Event.values()) {
                if (event.contains(ch)){
                    return event;
                }
            }
            return ANY;
        }
    }

    State currentState = State.q0;

    protected abstract State nextState(Event event);

    public boolean scan(String string){
        currentState = State.q0;
        Event event = null;
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            event = Event.getEvent(ch);
            currentState = nextState(event);

            if (currentState == State.ERROR){
                break;
            }
        }
        return currentState == State.q3;
    }
}
