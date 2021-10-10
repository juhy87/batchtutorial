package com.example.batchtutorial.batch.jobs;

import com.example.batchtutorial.batch.entity.ROImage;
import com.example.batchtutorial.batch.repository.ROImageRepository;
import com.example.batchtutorial.batch.repository.RORepository;
import com.example.batchtutorial.batch.tasklets.TutorialTasklet;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TutorialConfig {

    private final JobBuilderFactory jobBuilderFactory; // Job 빌더 생성용
    private final StepBuilderFactory stepBuilderFactory; // Step 빌더 생성용
    private final EntityManagerFactory entityManagerFactory; //

    private int chunkSize=6;

    // JobBuilderFactory를 통해서 tutorialJob을 생성

    @Autowired
    private RORepository roRepository;
    @Autowired
    private ROImageRepository roImageRepository;

//    @Bean
    public Job tutorialTaskletJob() {
        log.trace("Hi I'm {} log", "TRACE2");

        return jobBuilderFactory.get("tutorialJob3")
                .incrementer(new UniqueRunIdIncrementer())
                .start(this.tutorialTaskletStep())  // Step 설정
                .build();
    }

    // JobBuilderFactory를 통해서 tutorialJob을 생성
    @Bean
    public Job tutorialBigDataJob() {
        log.trace("Hi I'm {} log", "TRACE2");

        return jobBuilderFactory.get("tutorialJob3")
                .incrementer(new UniqueRunIdIncrementer())
                .start(this.tutorialReaderProcessorWriterStep())  // Step 설정
                .build();
    }

    // StepBuilderFactory를 통해서 tutorialStep을 생성
    @Bean
    public Step tutorialTaskletStep() {
        return stepBuilderFactory.get("simpleStep3")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info(">>>>>>> This is Step3");
//                    log.info(">>>>>>> requestDate ={}", 1);
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
                .tasklet(new TutorialTasklet()) // Tasklet 설정
                .build();
    }
    @Bean
    public Step tutorialReaderProcessorWriterStep() {

        return stepBuilderFactory.get("tutorialReaderProcessorWriterStep")
                .<ROImage, ROImage>chunk(chunkSize)
                .reader(jpaPagingRader())
                .processor(jpaPagingProcessor())  // not required
                .writer(jpaPagingItemWriter())
                .build();

    }
    private JpaPagingItemReader<ROImage> jpaPagingRader() {

        return new JpaPagingItemReaderBuilder<ROImage>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM ROImage" +
                        " p  ORDER BY id ASC")
                .build();
    };

    private ItemProcessor<ROImage, ROImage> jpaPagingProcessor() {

        return roImage ->{
            return roImage;
        };

    }


    private ItemWriter<ROImage> jpaPagingItemWriter() {
        return item -> {

            log.info("=======================");
            for (ROImage roImage: item) {
                log.info("Current roimage={} rodetail={},", roImage.getId(), roImage.toString());
            }
        };
    }



}