<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <#if publications??>
        <#list publications as publication>
            <div class="card mx-auto mb-3 mt-3" style="width: 800px;">
                <div style="height: 300px; overflow: hidden;">
                    <img src="/images/byName/${publication.avatarString}"
                         class="card-img-top"
                         alt="publication"
                         style="width: 100%; height: 100%; object-fit: cover;">
                </div>
                <div class="card-body">
                    <h5 class="card-title">${publication.title}</h5>
                    <p class="card-text">${publication.description!"undefined"}</p>
                    <p class="card-text"><small class="text-muted">Created date -> ${publication.publicationDate}</small></p>
                    <p class="card-text"><small class="text-muted"><#if publication.updateDate??>Updated date -> ${publication.updateDate}</#if></small></p>

                    <button type="button"
                            class="btn btn-primary toggle-comments"
                            onclick="toggleComments(${publication.id})"
                            id="toggleBtn-${publication.id}">
                        Add comment (${publication.comments?size!0})
                    </button>
                </div>

                <div id="comments-${publication.id}" class="comments-section" style="display: none;">
                    <div class="card-footer">
                        <div class="existing-comments mb-3">
                            <h6>Comments:</h6>
                            <#if publication.comments?? && publication.comments?size gt 0>
                                <#list publication.comments as comment>
                                    <div class="comment-item border-bottom pb-2 mb-2">
                                        <div class="d-flex align-items-start">
                                            <div class="me-3">
                                                <#if comment.user.avatar?? && comment.user.avatar.fileName??>
                                                    <img src="/images/byName/${comment.user.avatar.fileName}"
                                                         width="40"
                                                         height="40"
                                                         class="rounded-circle"
                                                         alt="${comment.user.email!}">
                                                <#else>
                                                    <div class="rounded-circle bg-secondary d-flex align-items-center justify-content-center"
                                                         style="width: 40px; height: 40px;">
                                                        <span class="text-white fw-bold">
                                                            ${comment.user.email[0]!?upper_case}
                                                        </span>
                                                    </div>
                                                </#if>
                                            </div>

                                            <div class="flex-grow-1">
                                                <div class="d-flex justify-content-between align-items-start">
                                                    <div>
                                                        <strong class="d-block">${comment.user.email!}</strong>
                                                        <small class="text-muted">${comment.createdDate!}</small>
                                                    </div>
                                                </div>
                                                <p class="mb-0 mt-1">${comment.content!}</p>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            <#else>
                                <p class="text-muted">No comments yet. Be the first to comment!</p>
                            </#if>
                        </div>

                        <form action="comments/add" method="post" class="add-comment-form">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>

                            <input type="hidden" name="publicationId" value="${publication.id}">
                            <div class="mb-2">
                                <textarea class="form-control"
                                          name="content"
                                          rows="3"
                                          placeholder="Write your comment..."
                                          required></textarea>
                            </div>
                            <button type="submit" class="btn btn-success btn-sm">Submit Comment</button>
                        </form>
                    </div>
                </div>
            </div>
        </#list>
    </#if>

    <#if items.getTotalElements() gt 0>
        <nav id="navigation" aria-label="..." class="d-flex justify-content-center">
            <ul class="pagination">
                <li class="page-item <#if items.first>disabled</#if>">
                    <a class="page-link" href="?page=${items.getNumber()-1}&size=${items.size}">
                        previous
                    </a>
                </li>

                <#list 1..items.getTotalPages() as page>
                    <li class="page-item <#if page = items.getNumber() + 1>active</#if>" aria-current="page">
                        <a class="page-link" href="?page=${page - 1}&size=${items.size}">${page}</a>
                    </li>
                </#list>

                <li class="page-item <#if items.last>disabled</#if>">
                    <a class="page-link" href="?page=${items.getNumber()+1}&size=${items.size}">
                        next
                    </a>
                </li>
            </ul>
        </nav>
    </#if>

    <script>
        function toggleComments(publicationId) {
            const commentsSection = document.getElementById('comments-' + publicationId);
            const toggleButton = document.getElementById('toggleBtn-' + publicationId);

            if (commentsSection.style.display === 'none') {
                commentsSection.style.display = 'block';
                toggleButton.textContent = 'Close comments';
                toggleButton.classList.remove('btn-primary');
                toggleButton.classList.add('btn-secondary');
            } else {
                commentsSection.style.display = 'none';
                toggleButton.textContent = 'Add comment (' + toggleButton.getAttribute('data-count') + ')';
                toggleButton.classList.remove('btn-secondary');
                toggleButton.classList.add('btn-primary');
            }
        }
    </script>

</@main.layout>