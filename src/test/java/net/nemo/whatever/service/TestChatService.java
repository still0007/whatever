package net.nemo.whatever.service;

import net.nemo.whatever.db.mapper.ChatMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import net.nemo.whatever.entity.Chat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tonyshi on 2017/3/18.
 */
public class TestChatService {

    @Mock
    private ChatMapper chatMapper;

    @InjectMocks
    private ChatService chatService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testfindById(){
        when(chatMapper.findById(1)).thenReturn(null);

        Assert.assertNull(chatService.findById(1));

    }

    @Test
    public void testListChats(){
        List<Chat> chats = Arrays.asList(mock(Chat.class));

        when(chatMapper.selectChats(1)).thenReturn(chats);

        Assert.assertEquals(1, chatService.listChats(1).size());
        verify(chatMapper, times(1)).selectChats(1);

    }
}
