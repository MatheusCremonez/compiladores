package Symbols;

import java.util.ArrayList;
import java.util.List;

public class TableOfSymbols {

	private List<Symbol> stackOfSymbols;

	public TableOfSymbols() {
		stackOfSymbols = new ArrayList<Symbol>();
	}

	public List<Symbol> getStackOfSymbols() {
		return stackOfSymbols;
	}

	public Symbol getSymbol(int index) {
		return stackOfSymbols.get(index);
	}

	public void insert(Symbol symbol) {
		stackOfSymbols.add(symbol);
	}

	public void insertTypeOnFunction(String type) {
		Symbol symbol = stackOfSymbols.get(stackOfSymbols.size() - 1);

		if (symbol instanceof Function && symbol.getType() == null) {
			stackOfSymbols.get(stackOfSymbols.size() - 1).setType(type);
		}
	}

	public void insertTypeOnVariable(String type) {

		for (int i = (stackOfSymbols.size() - 1); i > 0; i--) {
			if (stackOfSymbols.get(i) instanceof Variable) {
				if (stackOfSymbols.get(i).getType() == null) {
					stackOfSymbols.get(i).setType(type);
				}
			} else {
				break;
			}
		}
	}

	private boolean lookProgramName(String lexema) {
		if (lexema.equals(stackOfSymbols.get(0).getLexema())) {
			return true;
		}
		return false;
	}

	public boolean search(String lexema) {
		int i;

		for (i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Variable) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return true;
				}
			} else {
				break;
			}

		}

		for (int j = i; j >= 0; j--) {
			if ((stackOfSymbols.get(i) instanceof Procedure) || (stackOfSymbols.get(i) instanceof Function)) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return true;
				}
			}
		}

		return lookProgramName(lexema);
	}

	public int searchSymbol(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Variable || stackOfSymbols.get(i) instanceof Function) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return i;
				}
			}
		}
		return -1;
	}

	public boolean searchVariable(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Variable) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return true;
				}
			}
		}

		return lookProgramName(lexema);
	}

	public boolean searchProcedure(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Procedure) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return true;
				}
			}
		}

		return lookProgramName(lexema);
	}

	public int searchProcedureLabel(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Procedure) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return stackOfSymbols.get(i).getLabel();
				}
			}
		}

		return -1;
	}

	public boolean searchFunction(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Function) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return true;
				}
			}
		}

		return lookProgramName(lexema);
	}

	public int searchFunctionLabel(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Function) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return stackOfSymbols.get(i).getLabel();
				}
			}
		}

		return -1;
	}

	public String searchTypeOfVariableOrFunction(String lexema) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Variable || stackOfSymbols.get(i) instanceof Function) {
				if (lexema.equals(stackOfSymbols.get(i).getLexema())) {
					return stackOfSymbols.get(i).getType();
				}
			}
		}
		return null;
	}

	public int searchPositionOfVariable(String variable) {
		for (int i = (stackOfSymbols.size() - 1); i >= 0; i--) {
			if (stackOfSymbols.get(i) instanceof Variable) {
				if (variable.equals(stackOfSymbols.get(i).getLexema())) {
					return stackOfSymbols.get(i).getPosition();
				}
			}
		}
		return -1;
	}

	public void cleanLevel() {
		for (int i = (stackOfSymbols.size() - 1); i > 0; i--) {
			if (stackOfSymbols.get(i) instanceof Function || stackOfSymbols.get(i) instanceof Procedure) {
				if (stackOfSymbols.get(i).isNotClosed()) {
					stackOfSymbols.get(i).setClosed(true);
					break;
				} else {
					stackOfSymbols.remove(i);
				}
			} else {
				stackOfSymbols.remove(i);
			}
		}
//		System.out.println("Tabela Atualizada");
//		debugTable();
	}

	public void debugTable() {
		for (int i = 0; i < stackOfSymbols.size(); i++) {
			System.out.println(stackOfSymbols.get(i).getLexema() + " " + stackOfSymbols.get(i).getType() + " "
					+ stackOfSymbols.get(i).getPosition());
		}
	}

}
