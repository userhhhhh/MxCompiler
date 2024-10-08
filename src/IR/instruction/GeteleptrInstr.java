package IR.instruction;

import IR.IRBlock;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;
import Util.type.IRType;

import java.util.ArrayList;

public class GeteleptrInstr extends Instruction {

    public IRVariable result;
    public IRType type;
    public String className;
    public IREntity ptrValue;
    public ArrayList<IREntity> idxList = new ArrayList<>();

    public GeteleptrInstr(IRBlock parent, IRVariable result, String className, IREntity ptrValue, IRType type) {
        super(parent);
        this.result = result;
        this.className = className;
        this.ptrValue = ptrValue;
        this.type = type;
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append(result.toString()).append(" = getelementptr ");
        if(className.startsWith("%class..")) {
            ret.append(className);
        } else{
            ret.append(type.toString());
        }
        ret.append(", ptr ").append(ptrValue.toString());
        for (IREntity idx : idxList) {
            ret.append(", ").append(idx.type.toString()).append(" ").append(idx.toString());
        }
        ret.append("\n");
        return ret.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ArrayList<String> getUse(){
        ArrayList<String> ans = new ArrayList<>();
        if(ptrValue instanceof IRVariable){
            ans.add(ptrValue.toString());
        }
        for (IREntity idx : idxList) {
            if(idx instanceof IRVariable){
                ans.add(idx.toString());
            }
        }
        return ans;
    }

    @Override
    public String getDef() {
        return result.toString();
    }
}
