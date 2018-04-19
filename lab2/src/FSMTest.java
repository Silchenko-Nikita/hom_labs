import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScanArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of("()", true),
                Arguments.of("(,\nWQJDOJIWD, )", true),
                Arguments.of("(DAS)", true),
                Arguments.of("(DAS, )", true),
                Arguments.of("(,\n,\t, )", true),
                Arguments.of("(WQQWE, DQWDQW, DSA)", true),
                Arguments.of("(", false),
                Arguments.of("", false),
                Arguments.of("())", false),
                Arguments.of("(DSA,DASDSA)", false),
                Arguments.of("(dwqdwqdwq)", false),
                Arguments.of("(DSADSA12, ,)", false),
                Arguments.of("(DSADS,   DASDAS)", false));
    }
}

class FSMTest {
    SwitchFSM switchFSM;
    TransTableFSM transTableFSM;
    StateFSM stateFSM;

    static Stream<String> scanSuccessStringProvider(){
        return Stream.of("()", "(,\nWQJDOJIWD, )", "(DAS)", "(DAS, )", "(,\n,\t, )", "(WQQWE, DQWDQW, DSA)");
    }

    static Stream<String> scanFailureStringProvider(){
        return Stream.of("(", "", "())", "(DSA,DASDSA)", "(dwqdwqdwq)",
                "(DSADSA12, ,)", "(DSADS,   DASDAS)");
    }

    private void assertSuccess(String string){
        assertTrue(switchFSM.scan(string));
        assertTrue(transTableFSM.scan(string));
        assertTrue( stateFSM.scan(string));
    }

    private void assertFailure(String string){
        assertFalse(switchFSM.scan(string));
        assertFalse(transTableFSM.scan(string));
        assertFalse( stateFSM.scan(string));
    }

    @BeforeEach
    void beforeAll(){
        switchFSM = new SwitchFSM();
        transTableFSM = new TransTableFSM();
        stateFSM = new StateFSM();
    }

    @ParameterizedTest
    @ValueSource(strings = {"()", "(,\nWQJDOJIWD, )", "(DAS)", "(DAS, )", "(,\n,\t, )", "(WQQWE, DQWDQW, DSA)"})
    void scanSuccess(String string) {
        assertSuccess(string);
    }

    @ParameterizedTest
    @ValueSource(strings = {"(", "", "())", "(DSA,DASDSA)", "(dwqdwqdwq)",
    "(DSADSA12, ,)", "(DSADS,   DASDAS)"})
    void scanFailure(String string) {
        assertFailure(string);
    }

    @ParameterizedTest
    @MethodSource("scanSuccessStringProvider")
    void scanSuccessMethodSource(String string) {
        assertSuccess(string);
    }

    @ParameterizedTest
    @MethodSource("scanFailureStringProvider")
    void scanFailureMethodSource(String string) {
        assertFailure(string);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "tests-data/scan-success-strings.csv")
    void scanSuccessCsvFileSource(String string) {
        assertSuccess(string);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "tests-data/scan-failure-strings.csv")
    void scanFailureCsvFileSource(String string) {
        assertFailure(string);
    }

    @ParameterizedTest
    @ArgumentsSource(ScanArgsProvider.class)
    void scanSuccessArgumentsSource(String string, boolean isSuccess) {
        assertEquals(isSuccess, switchFSM.scan(string));
        assertEquals(isSuccess, transTableFSM.scan(string));
        assertEquals(isSuccess, stateFSM.scan(string));
    }
}