package services;

import data.Database;
import entities.Tasks;
import entities.User;
import entities.Work;
import utils.Constant;
import utils.Untils;

import java.time.LocalDate;
import java.util.*;

public class TasksService implements IAddDeleteUpdate{
    private static TasksService tasksService;
    public static synchronized TasksService getInstance(){
        if (tasksService == null){
            tasksService = new TasksService();
        }
        return tasksService;
    }

    public Tasks inputTasks(Scanner sc){
        System.out.println("Nhập tên nhiệm vụ");
        String taskName = sc.nextLine();
        System.out.println("Nhập mô tả nhiệm vụ");
        String taskDescription = sc.nextLine();
        System.out.println("Nhập thời gian tạo nhiệm vụ");
        LocalDate addDate = Untils.convertStringToDate(sc, "dd/MM/yyyy");
        System.out.println("Nhập thời gian bắt đầu làm nhiệm vụ");
        LocalDate startDate = Untils.convertStringToDate(sc,"dd/MM/yyyy");
        System.out.println("Nhập thời gian kết thúc nhiệm vụ");
        LocalDate endDate = Untils.convertStringToDate(sc,"dd/MM/yyyy");
        System.out.println("Nhập thái thái");
        String status = sc.nextLine();
        return new Tasks(taskName, taskDescription, addDate, startDate, endDate, status);
    }




    public void addTaskByWorkId(Scanner sc) {
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc);

        Work work = findWorkById(workId); // Tìm công việc theo ID
        if (work != null) {
            Tasks tasks = inputTasks(sc);
            tasks.setWorkId(workId);
            Database.tasks.add(tasks);
            System.out.println("Thêm nhiệm vụ thành công cho công việc ID: " + workId);
        } else {
            // Nếu không tìm thấy công việc, thông báo cho người dùng
            System.out.println("Không tìm thấy công việc với ID: " + workId);
        }
    }




    @Override
    public void remove(Scanner sc) {
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);

        if (work != null) {
            System.out.println("Danh sách nhiệm vụ thuộc công việc ID " + workId + ":");
            boolean foundTask = false;
            for (Tasks tasks : Database.tasks) {
                if (tasks.getWorkId() == workId) { // Kiểm tra ID công việc
                    System.out.println("ID Nhiệm Vụ: " + tasks.getId() + ", Tên Nhiệm Vụ: " + tasks.getTaskName());
                    foundTask = true; // Đánh dấu là đã tìm thấy ít nhất một nhiệm vụ
                }
            }
            if (!foundTask) {
                System.out.println("Không có nhiệm vụ nào cho công việc ID: " + workId);
                return; // Không có nhiệm vụ để xóa
            }

            System.out.println("Nhập ID nhiệm vụ muốn xóa:");
            int taskId = Untils.inputInteger(sc);
            Tasks taskToEdit = findTaskById(taskId);

            if (taskToEdit != null && taskToEdit.getWorkId() == workId) { // Kiểm tra nếu nhiệm vụ thuộc về công việc này
                Database.tasks.remove(taskToEdit); // Xóa nhiệm vụ
                System.out.println("Xóa thành công nhiệm vụ với ID: " + taskId);
            } else {
                System.out.println("Không tìm thấy nhiệm vụ hoặc nhiệm vụ không thuộc công việc ID: " + workId);
            }
        } else {
            System.out.println("Không tìm thấy công việc với ID: " + workId);
        }
    }

    @Override
    public void edit(Scanner sc) {
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);

        if (work != null) {
            System.out.println("Danh sách nhiệm vụ thuộc công việc ID " + workId + ":");
            boolean foundTask = false;
            for (Tasks tasks : Database.tasks) {
                if (tasks.getWorkId() == workId) { // Kiểm tra ID công việc
                    System.out.println("ID Nhiệm Vụ: " + tasks.getId() + ", Tên Nhiệm Vụ: " + tasks.getTaskName());
                    foundTask = true; // Đánh dấu là đã tìm thấy ít nhất một nhiệm vụ
                }
            }
            if (!foundTask) {
                System.out.println("Không có nhiệm vụ nào cho công việc ID: " + workId);
                return; // Không có nhiệm vụ để xóa
            }
            System.out.println("Nhập ID nhiệm vụ muốn sữa:");
            int taskId = Untils.inputInteger(sc);
            Tasks taskToEdit = findTaskById(taskId);
            if (taskToEdit != null && taskToEdit.getWorkId() == workId) { // Kiểm tra nếu nhiệm vụ thuộc về công việc này
                System.out.println("Nhập tên dự án mới (hiện tại: " + taskToEdit.getTaskName() + "):");
                String newTaskName = sc.nextLine();
                System.out.println("Nhập mô tả dự án mới (hiện tại: " + taskToEdit.getTaskDescription() + "):");
                String newTaskDescribe = sc.nextLine();
                System.out.println("Nhập thời gian khởi tạo dự (hiện tại" + taskToEdit.getAddDate() + "):");
                LocalDate nerAddDate = Untils.convertStringToDate(sc,"dd/MM/yyyy");
                System.out.println("Nhập thời gian bắt đầu mới (hiện tại: " + taskToEdit.getStartDate() + "):");
                LocalDate newStartDate = Untils.convertStringToDate(sc, "dd/MM/yyyy");
                System.out.println("Nhập thời gian kết thúc mới (hiện tại: " + taskToEdit.getEndDate() + "):");
                LocalDate newEndDate = Untils.convertStringToDate(sc, "dd/MM/yyyy");
                System.out.println("Nhập trạng thái mới (hiện tại: " + taskToEdit.getStatus()+ "):");
                String newStatus = sc.nextLine();
                taskToEdit.setTaskName(newTaskName);
                taskToEdit.setTaskDescription(newTaskDescribe);
                taskToEdit.setAddDate(nerAddDate);
                taskToEdit.setStartDate(newStartDate);
                taskToEdit.setEndDate(newEndDate);
                taskToEdit.setStatus(newStatus);
                System.out.println("Sữa thành công nhiệm vụ với ID: " + taskId);
            } else {
                System.out.println("Không tìm thấy nhiệm vụ hoặc nhiệm vụ không thuộc công việc ID: " + workId);
            }
        } else {
            System.out.println("Không tìm thấy công việc với ID: " + workId);
        }
    }

    @Override
    public void display(Scanner sc) {
        if (Database.tasks.isEmpty()) {
            System.out.println("Không có nhiệm vụ nào trong danh sách.");
            return; // Nếu không có nhiệm vụ, kết thúc phương thức
        }

        System.out.println("Danh sách tất cả các nhiệm vụ:");
        for (Tasks tasks : Database.tasks) {
            System.out.println("ID Công Việc: " + tasks.getWorkId() +
                    ", ID Nhiệm Vụ: " + tasks.getId() +
                    ", Tên: " + tasks.getTaskName() +
                    ", Ngày Tạo: " + tasks.getAddDate() +
                    ", Ngày Bắt Đầu: " + tasks.getStartDate() +
                    ", Ngày Kết Thúc: " + tasks.getEndDate() +
                    ", Trạng Thái: " + tasks.getStatus());
        }
    }

    public void viewTaskCreationHistory(){
        if (Database.tasks.isEmpty()) {
            System.out.println("Không có nhiệm vụ nào trong danh sách.");
            return; // Nếu không có nhiệm vụ, kết thúc phương thức
        }

        System.out.println("Danh sách tất cả các nhiệm vụ:");
        for (Tasks tasks : Database.tasks) {
            System.out.println("ID Công Việc: " + tasks.getWorkId() + " | " +
                    "ID Nhiệm Vụ: " + tasks.getId() + " | " +
                    "Tên: " + tasks.getTaskName() + " | " +
                    "Ngày Tạo: " + tasks.getAddDate());

        }
    }

    public void viewTaskAssignmentHistory(){
        if (Database.tasks.isEmpty()) {
            System.out.println("Không có nhiệm vụ nào trong danh sách.");
            return; // Nếu không có nhiệm vụ, kết thúc phương thức
        }

        System.out.println("Danh sách tất cả các nhiệm vụ:");
        for (Tasks tasks : Database.tasks) {
            System.out.println("ID Công Việc: " + tasks.getWorkId() + " | " +
                    "ID Nhiệm Vụ: " + tasks.getId() + " | " +
                    "Tên: " + tasks.getTaskName() + " | " +
                    "Ngày Tạo: " + tasks.getAddDate() + " | " +
                    "Trạng Thái: " + tasks.getStatus());

        }
    }
    // tìm kiếm thoe id của công việc
    public void findById(Scanner sc){
        System.out.println("Nhập ID công việc:");
        int workId = Untils.inputInteger(sc); // Nhập ID công việc từ người dùng
        Work work = findWorkById(workId);
        if (work != null){
            System.out.println("Nhập id nhiệm vụ muốn tìm kiếm");
            int taskId = Untils.inputInteger(sc);
            Tasks tasks = findTaskById(taskId);
            if (tasks != null){
                tasks.displayTask();
            }else {
                System.out.println("không tìm thấy nhiệm vụ");
            }
        }else {
            System.out.println("không tìm thấy công việc");
        }
    }

 public void searchTaskByName(Scanner sc) {
     System.out.println("Nhập tên nhiệm vụ bạn muốn tìm kiếm:");
     String searchName = sc.nextLine().trim(); // Nhập tên nhiệm vụ và loại bỏ khoảng trắng thừa
     boolean found = false; // Biến để kiểm tra xem có tìm thấy nhiệm vụ hay không

     System.out.println("Danh sách nhiệm vụ tìm thấy:");
     for (Tasks task : Database.tasks) {
         // Kiểm tra xem tên nhiệm vụ có chứa chuỗi tìm kiếm không, không phân biệt chữ hoa chữ thường
         if (task.getTaskName().toLowerCase().contains(searchName.toLowerCase())) {
             System.out.println("ID Công Việc: " + task.getWorkId() +
                     ", ID Nhiệm Vụ: " + task.getId() +
                     ", Tên: " + task.getTaskName() +
                     ", Ngày Tạo: " + task.getAddDate() +
                     ", Ngày Bắt Đầu: " + task.getStartDate() +
                     ", Ngày Kết Thúc: " + task.getEndDate() +
                     ", Trạng Thái: " + task.getStatus());
             found = true; // Đánh dấu là đã tìm thấy ít nhất một nhiệm vụ
         }
     }

     if (!found) {
         System.out.println("Không tìm thấy nhiệm vụ nào với tên: " + searchName);
     }
 }


 // giao nhiệm vụ cho câp dưới ( trưởng phòng giao nhân viên)
    public void assignTaskToStaff(Scanner sc, User currentUser, ArrayList<User> users) {
        // Kiểm tra xem người dùng hiện tại có quyền giao nhiệm vụ
        if (!currentUser.getRole().equals(Constant.ROLE_MANAGE)) {
            System.out.println("Chỉ trưởng phòng mới có quyền gán nhiệm vụ.");
            return;
        }

        try {
            System.out.println("Nhập ID công việc:");
            int workId = Untils.inputInteger(sc);
            Work work = findWorkById(workId);

            if (work == null) {
                System.out.println("Không tìm thấy công việc với ID: " + workId);
                return;
            }

            if (work.getAssignee() != currentUser.getId()) {
                System.out.println("Bạn không có quyền giao nhiệm vụ cho công việc này.");
                return;
            }

            System.out.println("Nhập ID nhiệm vụ muốn phân công:");
            int taskId = Untils.inputInteger(sc);
            Tasks task = findTaskById(taskId);

            if (task == null || task.getWorkId() != workId) {
                System.out.println("Không tìm thấy nhiệm vụ phù hợp với ID: " + taskId);
                return;
            }

            System.out.println("Nhập ID nhân viên để gán nhiệm vụ:");
            int employeeId = Untils.inputInteger(sc);
            User assignedUser = findUserById(employeeId, users); // Giả sử có phương thức này

            if (assignedUser == null || !assignedUser.getRole().equals(Constant.ROLE_STAFF)) {
                System.out.println("ID nhân viên không hợp lệ hoặc không phải là nhân viên.");
                return;
            }

            // Gán nhiệm vụ cho nhân viên
            task.setAssignee(employeeId);
            task.setWorkCreator(currentUser.getUsername());
            task.setStatus("Đã bàn giao cho nhân viên ID = " + employeeId);
            System.out.println("Nhiệm vụ \"" + task.getTaskName() + "\" đã được gán cho nhân viên ID: " + employeeId);

        } catch (InputMismatchException e) {
            System.out.println("Đã xảy ra lỗi khi nhập dữ liệu. Vui lòng nhập lại.");
            sc.nextLine(); // Xóa bộ đệm đầu vào để tránh vòng lặp vô hạn
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }


    public void displayAssignedTasksByUserId(int assignee) {
        // Kiểm tra xem có nhiệm vụ nào không
        if (Database.tasks.isEmpty()) {
            System.out.println("Không có nhiệm vụ nào trong danh sách.");
            return;
        }

        System.out.println("Danh sách nhiệm vụ đã được giao cho nhân viên với ID: " + assignee);
        boolean hasAssignedTasks = false;

        // Lặp qua tất cả các nhiệm vụ
        for (Tasks task : Database.tasks) {
            // Kiểm tra nếu nhiệm vụ thuộc về nhân viên hiện tại
            if (task.getAssignee() == assignee) {
                hasAssignedTasks = true;
                System.out.println(
                        " ID Nhiệm Vụ: " + task.getId() +
                        ", Tên: " + task.getTaskName() +
                        ", Mô Tả: " + task.getTaskDescription() +
                        ", Ngày Kết Thúc: " + task.getEndDate() +
                        ", Người Giao: " + " Trưởng phòng " +  task.getWorkCreator()); // Hiển thị yteen của người giao nhiệm vụ
            }
        }

        // Kiểm tra nếu không có nhiệm vụ nào của nhân viên hiện tại
        if (!hasAssignedTasks) {
            System.out.println("Không có nhiệm vụ nào đã được giao cho nhân viên với ID: " + assignee);
        }
    }

    // Hàm thực hiện nhiệm vụ và ghi nội dung
    public void performTheTask(Scanner sc) {
        System.out.println("Nhập ID nhiệm vụ để tiến hành làm nhiệm vụ:");
        int taskId = Untils.inputInteger(sc);
        Tasks tasks = findTaskById(taskId);

        if (tasks != null) {
            System.out.println("Tổng kết nhiệm vụ để báo cáo:");
            String progress = sc.nextLine(); // Nhập mô tả về tiến độ nhiệm vụ

            // Cập nhật mô tả nhiệm vụ với nội dung tiến độ
            tasks.setTaskDescription(progress); // Lưu mô tả vào nhiệm vụ

            System.out.println("Nội dung công việc đã được ghi lại.");
        } else {
            System.out.println("Không tìm thấy nhiệm vụ.");
        }
    }

    // Hàm gửi báo cáo nhiệm vụ cho cấp trên
    public void reportWorkToBoss(Scanner sc, User manager, ArrayList<User> bosses) {
        // Kiểm tra nếu người gửi là nhân viên
        if (!manager.getRole().equals(Constant.ROLE_STAFF)) {
            System.out.println("Chỉ có nhân viên mới có quyền gửi báo cáo.");
            return;
        }

        // Nhập ID nhiệm vụ để lấy mô tả
        System.out.println("Nhập ID nhiệm vụ để lấy nội dung báo cáo:");
        int taskId = Untils.inputInteger(sc);
        Tasks tasks = findTaskById(taskId); // Tìm nhiệm vụ theo ID

        if (tasks == null) {
            System.out.println("Không tìm thấy nhiệm vụ với ID: " + taskId);
            return;
        }

        System.out.println("Nhập ID của cấp trên để gửi báo cáo nhiệm vụ:");
        int bossId = Untils.inputInteger(sc); // Nhập ID cấp trên

        // Kiểm tra xem ID sếp có tồn tại không
        User boss = findUserById(bossId, bosses); // Tìm sếp theo ID

        // Gửi báo cáo nếu sếp tồn tại và là cấp trên hợp lệ
        if (boss != null && (boss.getRole().equals(Constant.ROLE_BOSS) || boss.getRole().equals(Constant.ROLE_MANAGE))) {
            // Thêm nội dung mô tả nhiệm vụ vào báo cáo
            String reportWithSender = "Báo cáo từ " + manager.getUsername() + " (ID: " + manager.getId() + "): " + tasks.getTaskDescription();

            // Kiểm tra xem danh sách thông báo của sếp đã được khởi tạo chưa
            if (boss.getNotifications() == null) {
                boss.setNotifications(new ArrayList<>()); // Khởi tạo danh sách thông báo nếu chưa có
            }

            // Thêm báo cáo vào danh sách thông báo của sếp
            boss.getNotifications().add(reportWithSender);

            System.out.println("Báo cáo nhiệm vụ đã được gửi đến sếp ID: " + bossId);
        } else {
            System.out.println("Không tìm thấy sếp với ID: " + bossId + " hoặc không phải là sếp/ trưởng phòng.");
        }
    }

    public void displayTaskEvaluationsIsStaff(int userId, ArrayList<User> users) {
        User user = findUserById(userId, users);

        // Kiểm tra xem nhân viên có tồn tại không
        if (user == null) {
            System.out.println("Không tìm thấy nhân viên với ID: " + userId);
            return;
        }

        // Kiểm tra xem nhân viên có thông báo nào không
        ArrayList<String> notifications = user.getNotifications();
        if (notifications == null || notifications.isEmpty()) {
            System.out.println("Không có đánh giá nào cho nhân viên ID: " + userId);
            return;
        }
        for (Tasks tasks: Database.tasks) {
            // Hiển thị nội dung đánh giá
            System.out.println("Danh sách đánh giá cho nhân viên ID: " + userId);
            for (String notification : notifications) {
                System.out.println("- " + notification + " Trưởng Phòng: " + tasks.getWorkCreator());
            }
        }

    }

    // Hàm hiển thị nội dung đánh giá của nhân viên theo ID
    public void displayTaskEvaluationsIsmanage(int userId, ArrayList<User> users) {
        User user = findUserById(userId, users);

        // Kiểm tra xem nhân viên có tồn tại không
        if (user == null) {
            System.out.println("Không tìm thấy nhân viên với ID: " + userId);
            return;
        }

        // Kiểm tra xem nhân viên có thông báo nào không
        ArrayList<String> notifications = user.getNotifications();
        if (notifications == null || notifications.isEmpty()) {
            System.out.println("Không có đánh giá nào cho nhân viên ID: " + userId);
            return;
        }
        // Hiển thị nội dung đánh giá
        System.out.println("Danh sách đánh giá cho nhân viên ID: " + userId);
        for (String notification : notifications) {
            System.out.println("- " + notification);
        }
    }


    public void sendTaskEvaluation(Scanner sc, ArrayList<User> users) {
        // Nhập ID nhiệm vụ
        System.out.println("Nhập ID nhiệm vụ để gửi đánh giá:");
        int taskId = Untils.inputInteger(sc);
        Tasks task = findTaskById(taskId);

        // Kiểm tra xem nhiệm vụ có tồn tại không
        if (task == null) {
            System.out.println("Không tìm thấy nhiệm vụ với ID: " + taskId);
            return;
        }

        // Nhập ID nhân viên
        System.out.println("Nhập ID cấp dưới để gửi đánh giá:");
        int userId = Untils.inputInteger(sc);
        User user = findUserById(userId, users);

        // Kiểm tra xem nhân viên có tồn tại không
        if (user == null) {
            System.out.println("Không tìm thấy nhân viên với ID: " + userId);
            return;
        }

        // Kiểm tra vai trò của người dùng, chỉ cho phép gửi đánh giá nếu là nhân viên (staff)
        if (!user.getRole().equals(Constant.ROLE_STAFF)) {
            System.out.println("Chỉ có thể gửi đánh giá cho nhân viên (staff). Người dùng có ID: " + userId + " không phải là nhân viên.");
            return;
        }

        // Nhập nội dung đánh giá
        System.out.println("Nhập nội dung đánh giá:");
        String evaluationContent = sc.nextLine();

        // Thêm đánh giá vào danh sách thông báo của nhân viên
        if (user.getNotifications() == null) {
            user.setNotifications(new ArrayList<>()); // Khởi tạo danh sách thông báo nếu chưa có
        }

        // Tạo thông báo đánh giá
        String evaluationMessage = "Đánh giá nhiệm vụ ID: " + taskId + " - " + evaluationContent;
        user.getNotifications().add(evaluationMessage);

        System.out.println("Đánh giá đã được gửi cho nhân viên ID: " + userId);
    }




    // tìm kiếm nhệm vụ theo id
    public Tasks findTaskById(int id) {
        for (Tasks tasks : Database.tasks) {
            if (tasks.getId() == id) {
                return tasks; // Trả về nhiệm vụ nếu tìm thấy
            }
        }
        return null; // Trả về null nếu không tìm thấy nhiệm vụ
    }

    // Tìm kiếm công việc theo ID
    public Work findWorkById(int workId) {
        for (Map.Entry<Integer, ArrayList<Work>> entry : Database.projectWorks.entrySet()) { // Lặp qua tất cả các dự án
            ArrayList<Work> works = entry.getValue(); // Danh sách công việc trong dự án
            for (Work work : works) {
                if (work.getId() == workId) {
                    return work; // Trả về công việc nếu tìm thấy
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy công việc
    }
    // Phương thức tìm người dùng theo ID
    private User findUserById(int userId, ArrayList<User> users) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
}
