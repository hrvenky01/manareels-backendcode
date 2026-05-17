-- =========================
-- PERFORMANCE INDEXES
-- =========================

CREATE INDEX IF NOT EXISTS idx_reels_created_at 
ON reels(created_at DESC);

CREATE INDEX IF NOT EXISTS idx_likes_reel_id 
ON likes(reel_id);

CREATE INDEX IF NOT EXISTS idx_comments_reel_id 
ON comments(reel_id);

CREATE INDEX IF NOT EXISTS idx_users_username 
ON users(username);