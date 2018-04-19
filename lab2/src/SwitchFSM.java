public class SwitchFSM extends FSM {

    @Override
    protected State nextState(Event event) {
        switch (currentState){
            case q0:
                switch (event){
                    case LEFT_QUOTE:
                        currentState = State.q1;
                        break;
                    default:
                        currentState = State.ERROR;
                        break;
                }
                break;
            case q1:
                switch (event){
                    case UPPER_LETTER:
                        currentState = State.q1;
                        break;
                    case COMMA:
                        currentState = State.q2;
                        break;
                    case RIGHT_QUOTE:
                        currentState = State.q3;
                        break;
                    default:
                        currentState = State.ERROR;
                        break;
                }
                break;
            case q2:
                switch (event){
                    case WHITESPACE:
                        currentState = State.q1;
                        break;
                    default:
                        currentState = State.ERROR;
                        break;
                }
                break;
            case q3:
                currentState = State.ERROR;
                break;
            case ERROR:
                break;
        }
        return currentState;
    }
}
