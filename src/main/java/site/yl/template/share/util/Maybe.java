package site.yl.template.share.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * Maybe<X> 意味着X可能为null.<p>
 * 使用Maybe将强迫在每个可能为null的地方做出思考与处理。
 * @param <X> 类型参数
 */
public class Maybe<X> {
    private ValueHolder<X> valueHolder;

    private Maybe(X value){
        if (value!=null){
            valueHolder =new ValueHolder(value);
        }
    }

    public  boolean isEmpty(){
        return valueHolder ==null;
    }

    //---------------------------------- 快捷方法

    public  static  <X> Maybe<X> empty(){
        return  new Maybe<>(null);
    }

    public static <X> Maybe<X> of(X value){
        return new Maybe<>(value);
    }

    public static  <RE extends Exception,X>  X ifNullRe(X value,Supplier<RE> function) throws RE{
        if (value==null){
            throw function.get();
        }else {
            return   value;
        }
    }


    public static  <CE extends Exception,X>  X ifNullCe(X value,Supplier<CE> function) throws CE{
        if (value==null){
            throw function.get();
        }else {
            return   value;
        }
    }

    @FunctionalInterface
    public interface VoidInVoidOut{
        void apply();
    }

    public static <X> void ifNotNull(X value,VoidInVoidOut func) {
        if (value!=null){
            func.apply();
        }
    }


    public static  <E,X> Maybe<E> ifNotNull(X value,Supplier<Maybe<E>> supplier) {
        if (value!=null){
            return supplier.get();
        }else {
            return Maybe.empty();
        }
    }


    //-------------------------------------处理null情况
    /**
     * 如果为X为null，返回默认值
     * @param defaultValue 默认值,不允许为null
     * @return
     */
    public X ifNull(X defaultValue){
        Objects.requireNonNull(defaultValue);
        if(isEmpty()){
            return defaultValue;
        }else {
            return valueHolder.get();
        }
    }

    /**
     * 绑定默认值，X为null时返回默认值
     * @param defaultValue 默认值,不允许为null
     * @return
     */
    public ValueHolder<X> bind(X defaultValue){
        Objects.requireNonNull(defaultValue);
        if(isEmpty()){
            return new ValueHolder<>(defaultValue);
        }else {
            return valueHolder;
        }
    }


    /**
     * 如果为null抛出受检异常
     * @param function 返回受检异常的函数
     * @param <CE> 受检异常类型参数
     * @return X
     * @throws CE 受检异常
     */
    public  <CE extends Exception> X ifNullCe(Supplier<CE> function) throws CE{
        if (isEmpty()){
            throw function.get();
        }else {
            return   valueHolder.get();
        }
    }


    /**
     * 如果为null抛出运行时异常
     * @param function 抛出运行时异常的函数
     * @param <RE> 运行时异常类型参数
     * @return X
     * @throws RE 运行时异常
     */
    public  <RE extends Exception> X  ifNullRe(Supplier<RE> function) throws RE{
        if (isEmpty()){
            throw function.get();
        }else {
            return   valueHolder.get();
        }
    }



    //-------------------------------------处理非null情况

    /**
     * 当X不为null是，消费X，并且无需返回值
     * @param consumerFunction 消费函数
     */
    public void ifNotNull(Consumer<? super X> consumerFunction) {
        if (!isEmpty()){
            valueHolder.accept(consumerFunction);
        }
    }

    /**
     * 当X不为null是，消费X，并且需返回一个Maybe<E>
     * @param consumerFunction 消费函数
     * @param <E> 类型参数
     * @return
     */
    public <E> Maybe<E> ifNotNull(Function<? super X,Maybe<E>> consumerFunction) {
        if (!isEmpty()){
            return valueHolder.accept(consumerFunction);
        }else {
            return Maybe.empty();
        }
    }


    //-------------------------------------映射
     /**
     * 将Maybe<X> 映射为Maybe<E>
     * @param mapper 映射函数
     * @param <E>    需要映射到的目标类型
     * @return
     */
    public <E> Maybe<E> map(Function<? super X, ? extends E> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty())
            return Maybe.empty();
        else {
            return Maybe.of(mapper.apply(valueHolder.get()));
        }
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(valueHolder.get());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Maybe)) {
            return false;
        }

        Maybe<?> other = (Maybe<?>) obj;
        if(this.isEmpty() && other.isEmpty()){
            return true;
        }else if(!this.isEmpty() && !other.isEmpty()){
            return Objects.equals(this.valueHolder.get(), other.valueHolder.get());
        }else {
            return false;
        }

    }


    @Override
    public String toString() {
        return !isEmpty()
                ? String.format("Maybe[%s]", valueHolder.get())
                : "Maybe.empty";
    }



    private class ValueHolder<X>{
        //value never null
        private final X value;

        private ValueHolder(X value){
            assert value!=null:"value can not be null";
            this.value=value;
        }


        public X get(){
            return value;
        }


        private void accept(Consumer<? super X> applyFunction) {
                applyFunction.accept(value);
        }


        private  <R> R accept(Function<? super X,R> function) {
            return   function.apply(value);
        }
    }

}
