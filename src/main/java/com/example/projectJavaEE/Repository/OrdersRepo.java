package com.example.projectJavaEE.Repository;

import com.example.projectJavaEE.Entites.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    List<Orders> findByLogin_IdAccountOrderByIdOrderDesc(int idAccount);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Orders o WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month AND DAY(o.orderDate) = :day AND (o.status LIKE '%hoan thanh%' OR o.status LIKE '%Completed%')")
    Double getDailyRevenue(int year, int month, int day);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Orders o WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month AND (o.status LIKE '%hoan thanh%' OR o.status LIKE '%Completed%')")
    Double getMonthlyRevenue(int year, int month);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Orders o WHERE YEAR(o.orderDate) = :year AND (o.status LIKE '%hoan thanh%' OR o.status LIKE '%Completed%')")
    Double getYearlyRevenue(int year);

    // 1. Cho bộ lọc NGÀY: Lấy doanh thu 7 ngày gần nhất (Dùng LocalDateTime và tách Year, Month, Day)
    @Query("SELECT YEAR(o.orderDate), MONTH(o.orderDate), DAY(o.orderDate), COALESCE(SUM(o.totalPrice), 0.0) " +
            "FROM Orders o " +
            "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "AND (o.status LIKE '%hoan thanh%' OR o.status LIKE '%Completed%') " +
            "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate), DAY(o.orderDate)")
    List<Object[]> getRevenueByDateRange(@Param("startDate") java.time.LocalDateTime startDate, @Param("endDate") java.time.LocalDateTime endDate);

    // 2. Cho bộ lọc THÁNG: Lấy doanh thu của từng ngày trong tháng
    @Query("SELECT DAY(o.orderDate), COALESCE(SUM(o.totalPrice), 0.0) " +
            "FROM Orders o " +
            "WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month " +
            "AND (o.status LIKE '%hoan thanh%' OR o.status LIKE '%Completed%') " +
            "GROUP BY DAY(o.orderDate)")
    List<Object[]> getRevenueDailyInMonth(@Param("year") int year, @Param("month") int month);

    // 3. Cho bộ lọc NĂM: Lấy doanh thu của 12 tháng
    @Query("SELECT MONTH(o.orderDate), COALESCE(SUM(o.totalPrice), 0.0) " +
            "FROM Orders o " +
            "WHERE YEAR(o.orderDate) = :year " +
            "AND (o.status LIKE '%hoan thanh%' OR o.status LIKE '%Completed%') " +
            "GROUP BY MONTH(o.orderDate)")
    List<Object[]> getRevenueMonthlyInYear(@Param("year") int year);
    List<Orders> getOrdersByLogin_IdAccount(int id);
}
