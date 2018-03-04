/*
 * Aleksandr Sidorov
 * Alva Studio ©2015
 * and open the template in the editor.
 */
package firstblockchain;

import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author alexsid
 */
public class FirstBlockchain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Blockchain blockchain = new Blockchain();
        for (int i = 0; i < 5; i++) {
            Block block = new Block();
            blockchain.addBlock(block);
        }
    }
    
}

class Block {
     public int index;
     public String dateCreated;
     public String prevHash;
     public String hash;
     public int nonce;

    public Block() {
        this.index = 0;
        this.dateCreated = new Date().toString();
        this.prevHash = "";
        this.hash = "";
        this.nonce = 0;
    }
    
    public String getKey() {
        return String.valueOf(this.index) + this.dateCreated + this.prevHash + String.valueOf(this.nonce);
    }
}

class Blockchain {
    ArrayList<Block> blocks = new ArrayList<>();
    
    public Blockchain() {
        addBlock(new Block());
    }

    public void addBlock(Block block) {
        
        if (this.blocks.isEmpty()) {
            System.out.println("------ Search First ----");
            block.prevHash = "0";
            block.hash = generateHash(block);
        } else {
            System.out.println("------ Search Next ----");
            Block prevBlock = getPrevBlock();
            block.prevHash = prevBlock.hash;
            block.index = this.blocks.size();
            block.hash = generateHash(block);
        }
        
        this.blocks.add(block);
        displayBlock(block);
    }
    
    private Block getPrevBlock() {
        return this.blocks.get(this.blocks.size()-1);
    }
    
    private void displayBlock(Block block) {
        System.out.println("------ Block " + block.index + " ---------");
        System.out.println("Date created : " + block.dateCreated);
        System.out.println("Nonce : " + block.nonce);
        System.out.println("Prev hash : " + block.prevHash);
        System.out.println("Hash : " + block.hash);
    }
    
    private String generateHash(Block block) {
        String hash = createHash(block.getKey());
        
        while (! hash.startsWith("00")) {
            block.nonce += 1;
            hash = createHash(block.getKey());
        }
        
        return hash;
    }
    
    private String createHash(String key) {
        return DigestUtils.sha1Hex(key);
    }
}
