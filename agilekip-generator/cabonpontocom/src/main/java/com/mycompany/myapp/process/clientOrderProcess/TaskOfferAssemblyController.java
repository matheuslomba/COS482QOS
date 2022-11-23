package com.mycompany.myapp.process.clientOrderProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-order-process/task-offer-assembly")
public class TaskOfferAssemblyController {

    private final Logger log = LoggerFactory.getLogger(TaskOfferAssemblyController.class);

    private final TaskOfferAssemblyService taskOfferAssemblyService;

    public TaskOfferAssemblyController(TaskOfferAssemblyService taskOfferAssemblyService) {
        this.taskOfferAssemblyService = taskOfferAssemblyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskOfferAssemblyContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskOfferAssemblyContextDTO taskOfferAssemblyContext = taskOfferAssemblyService.loadContext(id);
        return ResponseEntity.ok(taskOfferAssemblyContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskOfferAssemblyContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskOfferAssemblyContextDTO taskOfferAssemblyContext = taskOfferAssemblyService.claim(id);
        return ResponseEntity.ok(taskOfferAssemblyContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskOfferAssemblyContextDTO taskOfferAssemblyContext) {
        log.debug("REST request to complete ClientOrderProcess.TaskOfferAssembly {}", taskOfferAssemblyContext.getTaskInstance().getId());
        taskOfferAssemblyService.complete(taskOfferAssemblyContext);
        return ResponseEntity.noContent().build();
    }
}
