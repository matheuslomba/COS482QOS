package com.mycompany.myapp.process.clientOrderProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-order-process/task-proceed-checkout")
public class TaskProceedCheckoutController {

    private final Logger log = LoggerFactory.getLogger(TaskProceedCheckoutController.class);

    private final TaskProceedCheckoutService taskProceedCheckoutService;

    public TaskProceedCheckoutController(TaskProceedCheckoutService taskProceedCheckoutService) {
        this.taskProceedCheckoutService = taskProceedCheckoutService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskProceedCheckoutContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskProceedCheckoutContextDTO taskProceedCheckoutContext = taskProceedCheckoutService.loadContext(id);
        return ResponseEntity.ok(taskProceedCheckoutContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskProceedCheckoutContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskProceedCheckoutContextDTO taskProceedCheckoutContext = taskProceedCheckoutService.claim(id);
        return ResponseEntity.ok(taskProceedCheckoutContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskProceedCheckoutContextDTO taskProceedCheckoutContext) {
        log.debug(
            "REST request to complete ClientOrderProcess.TaskProceedCheckout {}",
            taskProceedCheckoutContext.getTaskInstance().getId()
        );
        taskProceedCheckoutService.complete(taskProceedCheckoutContext);
        return ResponseEntity.noContent().build();
    }
}
