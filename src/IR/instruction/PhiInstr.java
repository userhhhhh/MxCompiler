package IR.instruction;

import IR.IRBlock;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;
import Util.type.IRType;
import java.util.ArrayList;

public class PhiInstr extends Instruction {

    public IRVariable result = null;
    public IRType irType;
    public ArrayList<IREntity> values = new ArrayList<>();
    public ArrayList<IRBlock> blocks = new ArrayList<>();
    public ArrayList<Boolean> visited = new ArrayList<>();

    public PhiInstr(IRBlock parent, IRVariable result, IRType irType) {
        super(parent);
        this.result = result;
        this.irType = irType;
    }

    public void addBranch(IREntity value, IRBlock block) {
        values.add(value);
        blocks.add(block);
        visited.add(false);
    }

    public void replaceTarget(IRBlock oldBlock, IRBlock newBlock){
        for (int i = 0; i < blocks.size(); i++) {
            if(blocks.get(i) == oldBlock){
                blocks.set(i, newBlock);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(result.toString()).append(" = phi ").append(irType.toString()).append(" ");
        for (int i = 0; i < values.size(); i++) {
            ret.append("[ ").append(values.get(i).toString()).append(", %").append(blocks.get(i).name).append(" ]");
            if (i != values.size() - 1) {
                ret.append(", ");
            }
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
        for (IREntity entity : values) {
            // 排除数字的情况
            if (entity instanceof IRVariable irVariable && irVariable.toString().charAt(0) == '%') {
                ans.add(irVariable.toString());
            }
        }
        return ans;
    }

    @Override
    public String getDef(){
        return result.toString();
    }
}
