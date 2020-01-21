/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package firemni_system.models;

import javax.persistence.*;

/**
  Working plan object - stores hours working plan info for 31 days of one month
 */
@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @Column(name = "feedback_text")
    private String feedbackText;

    @Column(name = "reworks")
    private int reworks;

    @Column(name = "quality")
    private String quality;

    public Feedback() {
    }

    public Feedback(Domain domain, String feedbackText, int reworks, String quality) {
        this.domain = domain;
        this.feedbackText = feedbackText;
        this.reworks = reworks;
        this.quality = quality;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getReworks() {
        return reworks;
    }

    public void setReworks(int reworks) {
        this.reworks = reworks;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}