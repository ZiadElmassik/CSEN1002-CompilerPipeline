// Generated from /home/ubuntu/Repos/IdeaProjects/Compilers/LexicalAnalyzer/src/csen1002/main/task8/task8.g4 by ANTLR 4.10.1
package csen1002.main.task8;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class task8Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Q2=1, Q3=2, Q4=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Q0", "Q1", "Q2", "Q3", "Q4", "ZERO", "ONE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Q2", "Q3", "Q4"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public task8Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "task8.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0003S\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000\u001b\b\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000!\b\u0000"+
		"\n\u0000\f\u0000$\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000-\b\u0000\n\u0000\f\u0000"+
		"0\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003>\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004E\b\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0005\u0004K\b\u0004\n\u0004\f\u0004N\t\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0000\u0000\u0007\u0001"+
		"\u0000\u0003\u0000\u0005\u0001\u0007\u0002\t\u0003\u000b\u0000\r\u0000"+
		"\u0001\u0000\u0000Y\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0001.\u0001\u0000"+
		"\u0000\u0000\u00031\u0001\u0000\u0000\u0000\u00054\u0001\u0000\u0000\u0000"+
		"\u0007=\u0001\u0000\u0000\u0000\t?\u0001\u0000\u0000\u0000\u000bO\u0001"+
		"\u0000\u0000\u0000\rQ\u0001\u0000\u0000\u0000\u000f\u0010\u0003\u000b"+
		"\u0005\u0000\u0010\u0011\u0003\u000b\u0005\u0000\u0011\u0012\u0003\u000b"+
		"\u0005\u0000\u0012\u0013\u0003\u000b\u0005\u0000\u0013-\u0001\u0000\u0000"+
		"\u0000\u0014\u0015\u0003\u000b\u0005\u0000\u0015\u001a\u0003\u000b\u0005"+
		"\u0000\u0016\u0017\u0003\u000b\u0005\u0000\u0017\u0018\u0003\r\u0006\u0000"+
		"\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u001b\u0003\r\u0006\u0000\u001a"+
		"\u0016\u0001\u0000\u0000\u0000\u001a\u0019\u0001\u0000\u0000\u0000\u001b"+
		"\"\u0001\u0000\u0000\u0000\u001c!\u0003\r\u0006\u0000\u001d\u001e\u0003"+
		"\u000b\u0005\u0000\u001e\u001f\u0003\r\u0006\u0000\u001f!\u0001\u0000"+
		"\u0000\u0000 \u001c\u0001\u0000\u0000\u0000 \u001d\u0001\u0000\u0000\u0000"+
		"!$\u0001\u0000\u0000\u0000\" \u0001\u0000\u0000\u0000\"#\u0001\u0000\u0000"+
		"\u0000#%\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000%&\u0003\u000b"+
		"\u0005\u0000&\'\u0003\u000b\u0005\u0000\'-\u0001\u0000\u0000\u0000(-\u0003"+
		"\r\u0006\u0000)*\u0003\u000b\u0005\u0000*+\u0003\r\u0006\u0000+-\u0001"+
		"\u0000\u0000\u0000,\u000f\u0001\u0000\u0000\u0000,\u0014\u0001\u0000\u0000"+
		"\u0000,(\u0001\u0000\u0000\u0000,)\u0001\u0000\u0000\u0000-0\u0001\u0000"+
		"\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000/\u0002"+
		"\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u000012\u0003\u0001\u0000"+
		"\u000023\u0003\u000b\u0005\u00003\u0004\u0001\u0000\u0000\u000045\u0003"+
		"\u0003\u0001\u000056\u0003\u000b\u0005\u00006\u0006\u0001\u0000\u0000"+
		"\u000078\u0003\u0005\u0002\u000089\u0003\u000b\u0005\u00009>\u0001\u0000"+
		"\u0000\u0000:;\u0003\t\u0004\u0000;<\u0003\u000b\u0005\u0000<>\u0001\u0000"+
		"\u0000\u0000=7\u0001\u0000\u0000\u0000=:\u0001\u0000\u0000\u0000>\b\u0001"+
		"\u0000\u0000\u0000?D\u0003\u0005\u0002\u0000@A\u0003\u000b\u0005\u0000"+
		"AB\u0003\r\u0006\u0000BE\u0001\u0000\u0000\u0000CE\u0003\r\u0006\u0000"+
		"D@\u0001\u0000\u0000\u0000DC\u0001\u0000\u0000\u0000EL\u0001\u0000\u0000"+
		"\u0000FK\u0003\r\u0006\u0000GH\u0003\u000b\u0005\u0000HI\u0003\r\u0006"+
		"\u0000IK\u0001\u0000\u0000\u0000JF\u0001\u0000\u0000\u0000JG\u0001\u0000"+
		"\u0000\u0000KN\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000LM\u0001"+
		"\u0000\u0000\u0000M\n\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000"+
		"OP\u00050\u0000\u0000P\f\u0001\u0000\u0000\u0000QR\u00051\u0000\u0000"+
		"R\u000e\u0001\u0000\u0000\u0000\n\u0000\u001a \",.=DJL\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}