abstract class StateObj {
    FSM.State state = null;

    abstract StateObj nextState(FSM.Event event);
}

class Q0State extends StateObj {

    Q0State(){
        state = FSM.State.q0;
    }

    @Override
    StateObj nextState(FSM.Event event) {
        switch (event){
            case LEFT_QUOTE:
                return StateFSM.q1State;
            default:
                return StateFSM.errorState;
        }
    }
}

class Q1State extends StateObj {

    Q1State(){
        state = FSM.State.q1;
    }

    @Override
    StateObj nextState(FSM.Event event) {
        switch (event){
            case UPPER_LETTER:
                return StateFSM.q1State;
            case COMMA:
                return StateFSM.q2State;
            case RIGHT_QUOTE:
                return StateFSM.q3State;
            default:
                return StateFSM.errorState;
        }
    }
}

class Q2State extends StateObj {

    Q2State(){
        state = FSM.State.q2;
    }

    @Override
    StateObj nextState(FSM.Event event) {
        switch (event){
            case WHITESPACE:
                return StateFSM.q1State;
            default:
                return StateFSM.errorState;
        }
    }
}

class Q3State extends StateObj {

    Q3State(){
        state = FSM.State.q3;
    }

    @Override
    StateObj nextState(FSM.Event event) {
        return StateFSM.errorState;
    }
}

class ErrorState extends StateObj {

    ErrorState(){
        state = FSM.State.ERROR;
    }

    @Override
    StateObj nextState(FSM.Event event) {
        return StateFSM.errorState;
    }
}


public class StateFSM extends FSM {
    protected static final StateObj q0State = new Q0State();
    protected static final StateObj q1State = new Q1State();
    protected static final StateObj q2State = new Q2State();
    protected static final StateObj q3State = new Q3State();
    protected static final StateObj errorState = new ErrorState();

    private StateObj stateObj = q0State;

    @Override
    public boolean scan(String string){
        stateObj = q0State;
        return super.scan(string);
    }

    @Override
    protected State nextState(Event event) {
        stateObj = stateObj.nextState(event);
        currentState = stateObj.state;
        return currentState;
    }
}
