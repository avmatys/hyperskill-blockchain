package block;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import message.Message;

public class Block {
    
    private final long id;
    private final long magic;
    private final long timestamp;
    private final long minerId; 
    private final int miningTime;
    private final String hash;
    private final String prevHash;
    private final List<Message> messages;

    public Block(long id, long magic, long timestamp, long minerId, int miningTime, 
                 String hash, String prevHash, List<Message> messages) {
        this.id = id;
        this.magic = magic;
        this.timestamp = timestamp;
        this.minerId = minerId;
        this.miningTime = miningTime;
        this.hash = hash;
        this.prevHash = prevHash;
        this.messages = messages == null ? new ArrayList<>() : new ArrayList<>(messages);
    }


    public long getId() {
        return this.id;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPrevHash() {
        return this.prevHash;
    }

    public int getMiningTime() {
        return this.miningTime;
    }
    
    public List<Message> getMessages() {
        return Collections.unmodifiableList(this.messages);
    }

    @Override
    public String toString() {
        String allMessages = this.messages != null && !this.messages.isEmpty() 
                                ? "\n" + messages.stream()
                                                 .map(Message::getText)
                                                 .collect(Collectors.joining("\n"))
                                : "no messages";
        return "Block:\n" +
               "Created by miner # " + minerId + "\n" +
               "Id: " + id + "\n" +
               "Timestamp: " + timestamp + "\n" +
               "Magic number: " + magic + "\n" +
               "Hash of the previous block: \n" + prevHash + "\n" +
               "Hash of the block: \n" + hash + "\n" +
               "Block data: " + allMessages + "\n" +
               "Block was generating for " + miningTime + " seconds";
    }
    
}
