package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CacheMapTest {

    @Mock
    private Map map;

    private CacheMap cache;

    @BeforeEach
    public void setUp() {
        cache = new CacheMap(map);
    }

    @Test
    public void shouldCorrectCallMethodCalculate() {
        Object key = new Object();
        Object value = new Object();

        given(map.get(eq(key))).willReturn(null);

        Object result = cache.getOrPut(key, () -> value);

        assertThat(result)
                .isNotNull()
                .isEqualTo(value);

        verify(map, times(1)).get(eq(key));
        verify(map, times(1)).put(eq(key), eq(value));
    }

    @Test
    public void shouldNotCallMethodCalculate() {
        Object key = new Object();
        Object value = new Object();

        given(map.get(eq(key))).willReturn(value);

        Object result = cache.getOrPut(key, null);

        assertThat(result)
                .isNotNull()
                .isEqualTo(value);

        verify(map, times(1)).get(eq(key));
        verify(map, times(0)).put(any(), any());
    }
}
