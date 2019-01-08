package com.bumptech.glide.load.data;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.Preconditions;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores a mapping of data class to {@link com.bumptech.glide.load.data.DataRewinder.Factory} and
 * allows registration of new types and factories.
 */
public class DataRewinderRegistry {
  private final Map<Class<?>, DataRewinder.Factory<?>> rewinders = new HashMap<>();
  private static final DataRewinder.Factory<?> DEFAULT_FACTORY =
      new DataRewinder.Factory<Object>() {
        @NonNull
        @Override
        public DataRewinder<Object> build(@NonNull Object data) {
          return new DefaultRewinder(data);
        }

        @NonNull
        @Override
        public Class<Object> getDataClass() {
          throw new UnsupportedOperationException("Not implemented");
        }
      };

  public synchronized void register(@NonNull DataRewinder.Factory<?> factory) {
    //①：在ByteBufferRewinder.Factory中调用getDataClass()获取的key是ByteBuffer.class
    //②：在InputStreamRewinder.Factory中调用getDataClass()获取的key是InputStream.class
    rewinders.put(factory.getDataClass(), factory);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public synchronized <T> DataRewinder<T> build(@NonNull T data) {
    Preconditions.checkNotNull(data);
    //从rewinders集合中获取到和data.getClass()相匹配的Factory对象，从上面的分析中，我们知道rewinders注册的只有两个key，
    // 分别是ByteBuffer.class和InputStream.class，而我们又知道data.getClass()是一个InputStream.class，由此可以匹配成功。
    // 这个result就是InputStreamRewinder.Factory对象。
    DataRewinder.Factory<T> result = (DataRewinder.Factory<T>) rewinders.get(data.getClass());
    if (result == null) {
      //假如result没有匹配成功的话，也就是没有通过key匹配成功，那么就进行遍历rewinders集合，通过values值进行匹配，把匹配成功的Factory对象再赋值给result。
      for (DataRewinder.Factory<?> registeredFactory : rewinders.values()) {
        if (registeredFactory.getDataClass().isAssignableFrom(data.getClass())) {
          result = (DataRewinder.Factory<T>) registeredFactory;
          break;
        }
      }
    }

    //假如通过键key和值values都没有匹配成功，那么也不要紧，直接使用默认的DEFAULT_FACTORY
    if (result == null) {
      result = (DataRewinder.Factory<T>) DEFAULT_FACTORY;
    }
    //最后调用result的build方法。
    return result.build(data);
  }

  private static final class DefaultRewinder implements DataRewinder<Object> {
    private final Object data;

    DefaultRewinder(@NonNull Object data) {
      this.data = data;
    }

    @NonNull
    @Override
    public Object rewindAndGet() {
      return data;
    }

    @Override
    public void cleanup() {
      // Do nothing.
    }
  }
}
