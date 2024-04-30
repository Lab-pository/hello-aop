# hello-aop

## AOP(Aspect Oriented Programming)

AOP는 관점 지향 프로그래밍을 의미한다. 관점 지향이란 로직은 핵심적인 관점과 부가적인 관점으로 나누어서 보고, 그 관점을 기준으로 각각 모듈화하겠다는 것을 의미한다.

AOP는 OOP를 대체하는 개념이 아니라, 횡단 관심사를 처리하기 어려운 OOP의 부족한 부분을 보조하기 위한 목적으로 사용된다.

## AOP 용어 정리

- Advice : 부가 기능, 적용된 위치에서 Aspect에 의해 취해지는 조치를 의미한다.
- JoinPoint : Advice가 적용될 수 있는 지점을 의미한다.
- Aspect : 어드바이스와 포인트컷을 모듈화한 것을 의미한다.
- Pointcut : 조인 포인트 중에서 어드바이스가 적용될 위치를 선별하는 기능이다.
- Target : 어드바이스를 받는 객체, 포인트컷으로 결정한다.

## AspectJ

AOP의 대표적인 프레임워크이다. 스프링 AOP에 비해 사용하기 번거롭고 복잡하다. 런타임 시점에는 영향을 미치지 않으므로 컴파일이 완료된 이후에는 성능에 영향을 주지 않는다.

### AspectJ AOP 적용시점

#### compile-time weaving 

`java` 소스 코드를 컴파일해서 바이트코드로 만드는 시점에 부가 기능을 추가한다. 이 경우 AspectJ가 제공하는 컴파일러를 사용해야 한다. 이를 통해 컴파일된 `class` 파일을 디컴파일 해보면, 부가 기능 관련 코드가 들어가게 된다.

AspectJ 컴파일러는 Aspect를 확인해서 해당 클래스가 적용 대상인지 확인하고, 적용 대상인 경우 부가 기능 로직을 적용한다.

#### load-time weaving

자바를 실행하면 `class` 파일을 JVM 내부의 클래스 로더에 보관한다. 이 때 중간에서 `class` 파일을 조작해서 JVM에 올릴 수 있다. 이 시점에 Aspect를 적용하는 것을 load-time weaving이라고 한다.

#### post-compile weaving

컴파일 후 위빙하는 방식으로, binary weaving이라고도 부른다. 기존 클래스 파일과 JAR 파일을 위빙하는데 사용한다.

## 스프링 AOP

스프링 AOP는 스프링에서 사용할 수 있는 간단한 AOP 기능만을 제공한다. 런타임 시점에 프록시 객체를 활용해서 AOP를 적용한다.

### 런타임 시점 AOP 적용

런타임 시점은 컴파일도 끝나고, 클래스 로드에 클래스도 다 올라가서 이미 자바가 실행되고 난 다음을 의미한다. 즉, 자바의 메인 메서드가 실행된 다음이다. 따라서 자바 언어가 제공하는 범위 안에서 부가 기능을 적용해야 한다.

스프링과 같은 컨테이너의 도움과, 프록시, DI, 빈 포스트 프로세서와 같은 개념들의 도움을 받아 프록시를 통해 스프링 빈에 부가 기능을 적용한다.

프록시를 사용하기 때문에 AOP 기능에 제약이 있을 수 있지만, 특별한 컴파일러나 클래스로더 조작기를 활용하지 않아도 된다.

## 스프링 AOP 프록시 생성 방식의 차이

### JDK 동적 프록시

JDK 동적 프록시 방식은 인터페이스를 구현해서 프록시를 만든다.

#### 동작

1. 클라이언트가 프록시 객체의 메서드를 호출한다.
2. 프록시 객체는 `InvocationHandler` 에게 메서드 처리를 위임한다.
3. `InvocationHandler`는 `invoke()` 메서드를 실행해서 부가 기능을 실행한다.
4. 이후 `target` 에게 기능을 위임한다.

### CGLIB

CGLIB는 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리다.

GCLIB은 구체 클래스를 상속해서 동적 프록시를 만들어낼 수 있다. CGLIB는 원래 외부 라이브러리지만, 스프링 프레임워크가 스프링 내부 소스코드에 포함했다. 따라서 스프링을 사용한다면 따로 라이브러리를 추가하지 않아도 사용할 수 있다.

> CGLIB는 상속을 이용해 프록시를 생성하므로, `final` 키워드가 붙은 클래스나 메서드를 활용할 수 없다. 이런 경우 프록시가 생성되지 않거나 정상적으로 동작하지 않을 수 있다.

#### 동작

1. 클라이언트가 CGLIB 객체의 메서드를 실행한다.
2. CGLIB 객체는 `MethodInterceptor` 에게 메시드 처리를 위임한다.
3. `MethodInterceptor`가 부가 기능을 수행한다.
4. 이후 `target`에게 기능을 위임한다.

## CGLIB와 JDK 동적 프록시 관련 설정 방법

```yaml
spring:
  aop:
    proxy-target-class: true
```

스프링 부트는 기본적으로 CGLIB를 사용하도록 설정되어 있다. 이 값을 `false`로 수정하면 JDK 동적 프록시를 기본으로 사용하도록 설정된다.

### CGLIB가 기본으로 설정된 이유?

JDK 동적 프록시는 인터페이스를 기반으로 프록시를 생성하기 때문에 구체 클래스로 타입 캐스팅이 불가능하다는 한계가 있다.

CGLIB은 구체 클래스를 기반으로 프록시를 생성하기 때문에 위와 같은 문제가 발생하지 않는다.

이 문제가 중요한 이유는, 의존관계 주입 시 문제가 발생할 수 있기 때문이다.

```java
public class MemberService {
    private final MemberRepositoryImpl memberRepositoryImpl;

    public MemberService(MemberRespositoryImpl memberRepositoryImpl) {
        this.memberRepositoryImpl = memberRepositoryImpl;
    }
}
```

JDK 동적 프록시에 구체 클래스 타입을 주입하게 되면, 타입과 관련된 예외가 발생하게 된다. JDK 동적 프록시는 인터페이스 기반으로 프록시를 만드므로 `MemberRepositoryImpl` 이라는 구체 클래스에 대해 모르므로 의존관계 주입을 할 수 없다.

### 스프링에서 CGLIB를 적용하기 위해 해결한 문제들

#### 1. CGLIB를 따로 포함시켜야 했던 문제

스프링 3.2에서 CGLIB를 스프링 내부에 함께 패키징해서, 별도의 라이브러리를 추가하지 않아도 CGLIB를 사용할 수 있게 되었다.

#### 2. 생성자 2번 호출 문제

실제 `target` 객체를 생성할 때 생성자가 호출되고, 프록시 객체를 생성할 때 부모 클래스의 생성자가 호출되므로 생성자가 2번 호출되는 문제를 가지고 있었다.

스프링 4.0에서 `objeniss` 라이브러리를 활용해 문제를 해결했다.

#### 3. 대상 클래스에 기본 생성자 필수

CGLIB는 구체 클래스를 상속받으므로, CGLIB가 상속 시 부모 클래스의 생성자를 호출해야 하므로 기본 생성자가 필요했다.

스프링 4.0에서 `objeniss` 라이브러리를 통해 기본 생성자 없이 객체 생성이 가능하도록 했다.

## 프록시와 내부 호출

```java
public void external() { // AOP 적용
    // ...
    internal();
}
public void internal() { // AOP 적용
}
```

위의 예시에서 `external()`, `internal()` 두 메서드 모두 AOP가 적용되어 있다고 해보자. 그리고 `external()` 메서드에서 `internal()` 메서드를 호출하는 상황이다.

자바에서는 메서드 앞에 별도의 참조가 없다면, `this`로 자기 자신의 인스턴스를 가리킨다. 결과적으로 자기 자신의 내부 메서드를 호출하는 `this.internal()`이 되고 여기서 `this` 는 프록시 객체가 아닌 실제 대상 객체의 인스턴스를 의미한다. 결과적으로 이러한 내부 호출은 프록시를 거치지 않는다.

AspectJ를 활용해 AOP를 적용한다면 실제 코드에 AOP 코드가 붙으므로 내부 호출과 관계 없이 AOP가 적용되지만, 프록시 방식의 AOP는 메서드 내부 호출에 프록시를 적용할 수 없다.

### 해결 방법

#### 1. 자기 자신 주입

```java
public class MemberService {
    private MemberService memberService;
    
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
    
    public void external() {
        // ...
        memberService.internal();
    }
}
```

수정자 주입을 통해서 `MemberService` 를 주입해보자. 이렇게 AOP가 적용된 대상을 주입받으면, 주입받는 대상은 프록시 객체이다.

생성자 주입을 하게 되면, 자기 자신을 참조해야 하므로 순환 사이클이 발생해 오류가 발생한다.

#### 2. 지연 조회

```java
private final ObjectProvide<MemberService> memberServiceProvider;

public void external() {
    MemberService memberService = memberServiceProvider.getObject();
    memberService.internal();
}
```

스프링 빈을 지연해서 조회하는 방법으로, `ObjectProvider(provider)`, `ApplicationContext` 를 사용해 빈을 지연해서 가져오는 방법이다.

#### 3. 구조 변경

```java
public class MemberService {
    private InternalMemberService internalMemberService;
    
    public MemberService(InternalMemberService internalMemberService) {
        this.internalMemberService = internalMemberService;
    }
    
    public void external() {
        // ...
        internalMemberService.internal();
    }
}
```

내부 호출이 발생하지 않도록 구조를 변경하는 방법이다. 내부 호출을 다른 빈 객체로 분리하고, 외부 호출을 하던 객체에서 내부 호출을 하는 빈 객체를 주입받아서 호출하는 방법이다.

## Reference

- https://docs.spring.io/spring-framework/reference/core/aop.html
- [인프런 스프링 핵심 원리 - 고급편](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B3%A0%EA%B8%89%ED%8E%B8)
- https://jiwondev.tistory.com/152
