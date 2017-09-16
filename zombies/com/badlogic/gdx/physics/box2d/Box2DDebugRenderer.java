/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Box2DDebugRenderer
implements Disposable {
    protected ShapeRenderer renderer = new ShapeRenderer();
    private static final Vector2[] vertices = new Vector2[1000];
    private static final Vector2 lower = new Vector2();
    private static final Vector2 upper = new Vector2();
    private static final Array<Body> bodies = new Array();
    private static final Array<Joint> joints = new Array();
    private boolean drawBodies;
    private boolean drawJoints;
    private boolean drawAABBs;
    private boolean drawInactiveBodies;
    private boolean drawVelocities;
    private boolean drawContacts;
    public final Color SHAPE_NOT_ACTIVE = new Color(0.5f, 0.5f, 0.3f, 1.0f);
    public final Color SHAPE_STATIC = new Color(0.5f, 0.9f, 0.5f, 1.0f);
    public final Color SHAPE_KINEMATIC = new Color(0.5f, 0.5f, 0.9f, 1.0f);
    public final Color SHAPE_NOT_AWAKE = new Color(0.6f, 0.6f, 0.6f, 1.0f);
    public final Color SHAPE_AWAKE = new Color(0.9f, 0.7f, 0.7f, 1.0f);
    public final Color JOINT_COLOR = new Color(0.5f, 0.8f, 0.8f, 1.0f);
    public final Color AABB_COLOR = new Color(1.0f, 0.0f, 1.0f, 1.0f);
    public final Color VELOCITY_COLOR = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    private static Vector2 t = new Vector2();
    private static Vector2 axis = new Vector2();
    private final Vector2 f = new Vector2();
    private final Vector2 v = new Vector2();
    private final Vector2 lv = new Vector2();

    public Box2DDebugRenderer() {
        this(true, true, false, true, false, true);
    }

    public Box2DDebugRenderer(boolean drawBodies, boolean drawJoints, boolean drawAABBs, boolean drawInactiveBodies, boolean drawVelocities, boolean drawContacts) {
        for (int i = 0; i < vertices.length; ++i) {
            Box2DDebugRenderer.vertices[i] = new Vector2();
        }
        this.drawBodies = drawBodies;
        this.drawJoints = drawJoints;
        this.drawAABBs = drawAABBs;
        this.drawInactiveBodies = drawInactiveBodies;
        this.drawVelocities = drawVelocities;
        this.drawContacts = drawContacts;
    }

    public void render(World world, Matrix4 projMatrix) {
        this.renderer.setProjectionMatrix(projMatrix);
        this.renderBodies(world);
    }

    private void renderBodies(World world) {
        this.renderer.begin(ShapeRenderer.ShapeType.Line);
        if (this.drawBodies || this.drawAABBs) {
            world.getBodies(bodies);
            for (Body body : bodies) {
                if (!body.isActive() && !this.drawInactiveBodies) continue;
                this.renderBody(body);
            }
        }
        if (this.drawJoints) {
            world.getJoints(joints);
            for (Joint joint : joints) {
                this.drawJoint(joint);
            }
        }
        this.renderer.end();
        if (this.drawContacts) {
            this.renderer.begin(ShapeRenderer.ShapeType.Point);
            for (Contact contact : world.getContactList()) {
                this.drawContact(contact);
            }
            this.renderer.end();
        }
    }

    protected void renderBody(Body body) {
        Transform transform = body.getTransform();
        for (Fixture fixture : body.getFixtureList()) {
            if (this.drawBodies) {
                this.drawShape(fixture, transform, this.getColorByBody(body));
                if (this.drawVelocities) {
                    Vector2 position = body.getPosition();
                    this.drawSegment(position, body.getLinearVelocity().add(position), this.VELOCITY_COLOR);
                }
            }
            if (!this.drawAABBs) continue;
            this.drawAABB(fixture, transform);
        }
    }

    private Color getColorByBody(Body body) {
        if (!body.isActive()) {
            return this.SHAPE_NOT_ACTIVE;
        }
        if (body.getType() == BodyDef.BodyType.StaticBody) {
            return this.SHAPE_STATIC;
        }
        if (body.getType() == BodyDef.BodyType.KinematicBody) {
            return this.SHAPE_KINEMATIC;
        }
        if (!body.isAwake()) {
            return this.SHAPE_NOT_AWAKE;
        }
        return this.SHAPE_AWAKE;
    }

    private void drawAABB(Fixture fixture, Transform transform) {
        if (fixture.getType() == Shape.Type.Circle) {
            CircleShape shape = (CircleShape)fixture.getShape();
            float radius = shape.getRadius();
            vertices[0].set(shape.getPosition());
            transform.mul(vertices[0]);
            lower.set(Box2DDebugRenderer.vertices[0].x - radius, Box2DDebugRenderer.vertices[0].y - radius);
            upper.set(Box2DDebugRenderer.vertices[0].x + radius, Box2DDebugRenderer.vertices[0].y + radius);
            vertices[0].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.lower.y);
            vertices[1].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.lower.y);
            vertices[2].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.upper.y);
            vertices[3].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.upper.y);
            this.drawSolidPolygon(vertices, 4, this.AABB_COLOR, true);
        } else if (fixture.getType() == Shape.Type.Polygon) {
            PolygonShape shape = (PolygonShape)fixture.getShape();
            int vertexCount = shape.getVertexCount();
            shape.getVertex(0, vertices[0]);
            lower.set(transform.mul(vertices[0]));
            upper.set(lower);
            for (int i = 1; i < vertexCount; ++i) {
                shape.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
                Box2DDebugRenderer.lower.x = Math.min(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.vertices[i].x);
                Box2DDebugRenderer.lower.y = Math.min(Box2DDebugRenderer.lower.y, Box2DDebugRenderer.vertices[i].y);
                Box2DDebugRenderer.upper.x = Math.max(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.vertices[i].x);
                Box2DDebugRenderer.upper.y = Math.max(Box2DDebugRenderer.upper.y, Box2DDebugRenderer.vertices[i].y);
            }
            vertices[0].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.lower.y);
            vertices[1].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.lower.y);
            vertices[2].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.upper.y);
            vertices[3].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.upper.y);
            this.drawSolidPolygon(vertices, 4, this.AABB_COLOR, true);
        }
    }

    private void drawShape(Fixture fixture, Transform transform, Color color) {
        if (fixture.getType() == Shape.Type.Circle) {
            CircleShape circle = (CircleShape)fixture.getShape();
            t.set(circle.getPosition());
            transform.mul(t);
            this.drawSolidCircle(t, circle.getRadius(), axis.set(transform.vals[2], transform.vals[3]), color);
            return;
        }
        if (fixture.getType() == Shape.Type.Edge) {
            EdgeShape edge = (EdgeShape)fixture.getShape();
            edge.getVertex1(vertices[0]);
            edge.getVertex2(vertices[1]);
            transform.mul(vertices[0]);
            transform.mul(vertices[1]);
            this.drawSolidPolygon(vertices, 2, color, true);
            return;
        }
        if (fixture.getType() == Shape.Type.Polygon) {
            PolygonShape chain = (PolygonShape)fixture.getShape();
            int vertexCount = chain.getVertexCount();
            for (int i = 0; i < vertexCount; ++i) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            this.drawSolidPolygon(vertices, vertexCount, color, true);
            return;
        }
        if (fixture.getType() == Shape.Type.Chain) {
            ChainShape chain = (ChainShape)fixture.getShape();
            int vertexCount = chain.getVertexCount();
            for (int i = 0; i < vertexCount; ++i) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            this.drawSolidPolygon(vertices, vertexCount, color, false);
        }
    }

    private void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
        float angle = 0.0f;
        float angleInc = 0.31415927f;
        this.renderer.setColor(color.r, color.g, color.b, color.a);
        int i = 0;
        while (i < 20) {
            this.v.set((float)Math.cos(angle) * radius + center.x, (float)Math.sin(angle) * radius + center.y);
            if (i == 0) {
                this.lv.set(this.v);
                this.f.set(this.v);
            } else {
                this.renderer.line(this.lv.x, this.lv.y, this.v.x, this.v.y);
                this.lv.set(this.v);
            }
            ++i;
            angle += angleInc;
        }
        this.renderer.line(this.f.x, this.f.y, this.lv.x, this.lv.y);
        this.renderer.line(center.x, center.y, 0.0f, center.x + axis.x * radius, center.y + axis.y * radius, 0.0f);
    }

    private void drawSolidPolygon(Vector2[] vertices, int vertexCount, Color color, boolean closed) {
        this.renderer.setColor(color.r, color.g, color.b, color.a);
        this.lv.set(vertices[0]);
        this.f.set(vertices[0]);
        for (int i = 1; i < vertexCount; ++i) {
            Vector2 v = vertices[i];
            this.renderer.line(this.lv.x, this.lv.y, v.x, v.y);
            this.lv.set(v);
        }
        if (closed) {
            this.renderer.line(this.f.x, this.f.y, this.lv.x, this.lv.y);
        }
    }

    private void drawJoint(Joint joint) {
        Body bodyA = joint.getBodyA();
        Body bodyB = joint.getBodyB();
        Transform xf1 = bodyA.getTransform();
        Transform xf2 = bodyB.getTransform();
        Vector2 x1 = xf1.getPosition();
        Vector2 x2 = xf2.getPosition();
        Vector2 p1 = joint.getAnchorA();
        Vector2 p2 = joint.getAnchorB();
        if (joint.getType() == JointDef.JointType.DistanceJoint) {
            this.drawSegment(p1, p2, this.JOINT_COLOR);
        } else if (joint.getType() == JointDef.JointType.PulleyJoint) {
            PulleyJoint pulley = (PulleyJoint)joint;
            Vector2 s1 = pulley.getGroundAnchorA();
            Vector2 s2 = pulley.getGroundAnchorB();
            this.drawSegment(s1, p1, this.JOINT_COLOR);
            this.drawSegment(s2, p2, this.JOINT_COLOR);
            this.drawSegment(s1, s2, this.JOINT_COLOR);
        } else if (joint.getType() == JointDef.JointType.MouseJoint) {
            this.drawSegment(joint.getAnchorA(), joint.getAnchorB(), this.JOINT_COLOR);
        } else {
            this.drawSegment(x1, p1, this.JOINT_COLOR);
            this.drawSegment(p1, p2, this.JOINT_COLOR);
            this.drawSegment(x2, p2, this.JOINT_COLOR);
        }
    }

    private void drawSegment(Vector2 x1, Vector2 x2, Color color) {
        this.renderer.setColor(color);
        this.renderer.line(x1.x, x1.y, x2.x, x2.y);
    }

    private void drawContact(Contact contact) {
        WorldManifold worldManifold = contact.getWorldManifold();
        if (worldManifold.getNumberOfContactPoints() == 0) {
            return;
        }
        Vector2 point = worldManifold.getPoints()[0];
        this.renderer.setColor(this.getColorByBody(contact.getFixtureA().getBody()));
        this.renderer.point(point.x, point.y, 0.0f);
    }

    public boolean isDrawBodies() {
        return this.drawBodies;
    }

    public void setDrawBodies(boolean drawBodies) {
        this.drawBodies = drawBodies;
    }

    public boolean isDrawJoints() {
        return this.drawJoints;
    }

    public void setDrawJoints(boolean drawJoints) {
        this.drawJoints = drawJoints;
    }

    public boolean isDrawAABBs() {
        return this.drawAABBs;
    }

    public void setDrawAABBs(boolean drawAABBs) {
        this.drawAABBs = drawAABBs;
    }

    public boolean isDrawInactiveBodies() {
        return this.drawInactiveBodies;
    }

    public void setDrawInactiveBodies(boolean drawInactiveBodies) {
        this.drawInactiveBodies = drawInactiveBodies;
    }

    public boolean isDrawVelocities() {
        return this.drawVelocities;
    }

    public void setDrawVelocities(boolean drawVelocities) {
        this.drawVelocities = drawVelocities;
    }

    public boolean isDrawContacts() {
        return this.drawContacts;
    }

    public void setDrawContacts(boolean drawContacts) {
        this.drawContacts = drawContacts;
    }

    public static Vector2 getAxis() {
        return axis;
    }

    public static void setAxis(Vector2 axis) {
        Box2DDebugRenderer.axis = axis;
    }

    @Override
    public void dispose() {
        this.renderer.dispose();
    }
}

