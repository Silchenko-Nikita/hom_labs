import java.util.ArrayList;

public class TransTableFSM extends SwitchFSM {
    class Transition {
        State startState;
        Event trigger;
        State endState;

        Transition(State startState, Event trigger, State endState){
            this.startState = startState;
            this.trigger = trigger;
            this.endState = endState;
        }
    }
    private ArrayList<Transition> transitions;

    private void addTransition(State startState, Event trigger, State endState){
        transitions.add(new Transition(startState, trigger, endState));
    }

    private void buildTransitionTable(){
        addTransition(State.q0, Event.LEFT_QUOTE, State.q1);
        addTransition(State.q1, Event.UPPER_LETTER, State.q1);
        addTransition(State.q1, Event.COMMA, State.q2);
        addTransition(State.q1, Event.RIGHT_QUOTE, State.q3);
        addTransition(State.q2, Event.WHITESPACE, State.q1);
    }

    public TransTableFSM(){
        transitions = new ArrayList<Transition>();
        buildTransitionTable();
    }

    @Override
    protected State nextState(Event event) {
        for (Transition transition: transitions) {
            if (transition.startState == currentState &&
                transition.trigger == event){
                currentState = transition.endState;
                return currentState;
            }
        }
        return State.ERROR;
    }
}
